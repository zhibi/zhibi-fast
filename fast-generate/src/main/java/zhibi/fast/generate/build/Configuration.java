package zhibi.fast.generate.build;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import zhibi.fast.commons.utils.PackageScannerUtils;
import zhibi.fast.commons.utils.ReplaceUtils;
import zhibi.fast.generate.config.*;
import zhibi.fast.generate.convert.MySqlTypeConvert;
import zhibi.fast.generate.convert.TypeConvert;
import zhibi.fast.generate.database.DataBaseQuery;
import zhibi.fast.generate.database.DataBaseUpdate;
import zhibi.fast.generate.database.query.MySqlQuery;
import zhibi.fast.generate.database.update.MySqlUpdate;
import zhibi.fast.generate.dto.*;
import zhibi.fast.generate.enums.ColumnType;
import zhibi.fast.generate.enums.NamingStrategy;
import zhibi.fast.generate.enums.SyncStrategy;

import javax.persistence.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置汇总
 *
 * @author 执笔
 * @date 2019/4/18 21:26
 */
@Slf4j
public class Configuration {

    @Getter
    private DataSourceConfig dataSourceConfig;
    @Getter
    private PackageConfig    packageConfig;
    @Getter
    private GlobalConfig     globalConfig;
    @Getter
    private StrategyConfig   strategyConfig;
    @Getter
    private TemplateConfig   templateConfig;

    /**
     * 数据库操作语句
     * 根据数据库的不同而不同
     */
    private DataBaseQuery dataBaseQuery;

    @Getter
    private TypeConvert    typeConvert;
    @Getter
    private DataBaseUpdate dataBaseUpdate;


    //暴露出去
    @Getter
    private List<TableInfo>   tableList;
    @Getter
    private List<EntityInfo>  entityList;
    /**
     * 数据库的全部表格
     */
    @Getter
    private List<TableEntity> tableEntityList;
    @Getter
    private PackInfo          packInfo;
    /**
     * 数据库连接
     */
    private Connection        connection;

    public Configuration(DataSourceConfig dataSourceConfig, PackageConfig packageConfig, GlobalConfig globalConfig, StrategyConfig strategyConfig, TemplateConfig templateConfig) throws SQLException, IOException, ClassNotFoundException {
        if (dataSourceConfig == null) {
            this.dataSourceConfig = new DataSourceConfig();
        } else {
            this.dataSourceConfig = dataSourceConfig;
        }
        if (packageConfig == null) {
            this.packageConfig = new PackageConfig();
        } else {
            this.packageConfig = packageConfig;
        }
        if (globalConfig == null) {
            this.globalConfig = new GlobalConfig();
        } else {
            this.globalConfig = globalConfig;
        }
        if (strategyConfig == null) {
            this.strategyConfig = new StrategyConfig();
        } else {
            this.strategyConfig = strategyConfig;
        }
        if (templateConfig == null) {
            this.templateConfig = new TemplateConfig();
        } else {
            this.templateConfig = templateConfig;
        }

        // 初始化包信息
        init();

        //读取数据库初始化tableList
        initTableList();

        //初始化entityList
        initEntityList();

        //生成 转换
        transform();
    }

    /**
     * 转换
     */
    private void transform() {
        tableEntityList = new ArrayList<>();
        if (strategyConfig.getSync() == SyncStrategy.DATABASE_TO_ENTITY) {
            transformByDatabase();
        } else {
            transformByEntity();
        }
    }

    /**
     * 根据实体转换
     */
    private void transformByEntity() {
        entityList.forEach(entity -> {
            List<ColumnField> columnFields = new ArrayList<>();
            TableEntity       tableEntity  = new TableEntity();
            tableEntityList.add(tableEntity);
            tableEntity.setEntityName(entity.getName())
                    .setTableName(entityNameToTableName(entity))
                    .setColumnFields(columnFields);
            // 生成处实体类以外的其它的名字
            otherName(tableEntity);
            // 判断实体里面有没有对应的属性
            entity.getFields().forEach(field -> {
                if (!field.isAnnotationPresent(Transient.class)) {
                    ColumnField columnField = new ColumnField()
                            .setFieldName(field.getName())
                            .setColumnName(fieldNameToColumnName(field.getName()))
                            .setColumnType(fieldTypeToColumnType(field.getType().getSimpleName()))
                            .setFieldType(field.getType().getSimpleName());
                    handlerAnnotation(field, columnField);
                    columnFields.add(columnField);
                }
            });
        });
    }

    /**
     * 处理注解
     *
     * @param field
     * @param columnField
     */
    private void handlerAnnotation(Field field, ColumnField columnField) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (StringUtils.isNotEmpty(column.name())) {
                columnField.setColumnName(column.name());
            }
            if ("TEXT".equalsIgnoreCase(column.columnDefinition())) {
                columnField.setColumnType(ColumnType.TEXT);
            }
        }

        if (field.isAnnotationPresent(Id.class)) {
            columnField.setKeyFlag(true);
            if (field.isAnnotationPresent(GeneratedValue.class)) {
                GenerationType strategy = field.getAnnotation(GeneratedValue.class).strategy();
                if (strategy == GenerationType.IDENTITY) {
                    columnField.setIdentityFlag(true);
                }
            }
        }
    }

    /**
     * 类型转换
     *
     * @param type
     * @return
     */
    private ColumnType fieldTypeToColumnType(String type) {
        return typeConvert.processByFieldType(type);
    }

    /**
     * 属性名转成列名
     *
     * @param name
     * @return
     */
    private String fieldNameToColumnName(String name) {
        if (strategyConfig.getColumnNaming() == NamingStrategy.UNDERLINE_MUTUAL_CAMEL)
            return ReplaceUtils.camelToUnderline(name);
        return name;
    }

    /**
     * 生成其它名字
     *
     * @param tableEntity
     */
    private void otherName(TableEntity tableEntity) {
        tableEntity.setMapperName(String.format(globalConfig.getMapperName(), tableEntity.getEntityName()))
                .setServiceName(String.format(globalConfig.getServiceName(), tableEntity.getEntityName()))
                .setServiceImplName(String.format(globalConfig.getServiceImplName(), tableEntity.getEntityName()))
                .setXmlName(String.format(globalConfig.getXmlName(), tableEntity.getEntityName()))
                .setControllerName(String.format(globalConfig.getControllerName(), tableEntity.getEntityName()));
    }

    /**
     * 把实体名转换成表名
     *
     * @param entity
     * @return
     */
    private String entityNameToTableName(EntityInfo entity) {
        if (StringUtils.isNotEmpty(entity.getTableName())) return entity.getTableName();
        if (strategyConfig.getTableNaming() == NamingStrategy.UNDERLINE_MUTUAL_CAMEL)
            return ReplaceUtils.camelToUnderline(entity.getName());
        return entity.getName();
    }

    /**
     * 根据数据库表转换
     */
    private void transformByDatabase() {
        tableList.forEach(table -> {
            List<ColumnField> columnFields = new ArrayList<>();
            TableEntity       tableEntity  = new TableEntity();
            tableEntityList.add(tableEntity);
            tableEntity.setTableName(table.getName())
                    .setComment(table.getComment())
                    .setEntityName(tableNameToEntityName(table.getName()))
                    .setColumnFields(columnFields);
            // 生成处实体类以外的其它的名字
            otherName(tableEntity);
            // 列处理
            table.getFields().forEach(column -> {
                ColumnField columnField = new ColumnField()
                        .setComment(column.getComment())
                        .setColumnName(column.getName())
                        .setFieldName(columnNameToFieldName(column.getName()))
                        .setColumnType(columnTypeToColumnType(column.getType()))
                        .setFieldType(columnTypeToColumnType(column.getType()).getType())
                        .setKeyFlag(column.isKeyFlag())
                        .setIdentityFlag(column.isIdentityFlag());
                columnFields.add(columnField);
            });
        });
    }

    /**
     * 类型转换
     *
     * @param type
     * @return
     */
    private ColumnType columnTypeToColumnType(String type) {
        return typeConvert.processByColumnType(type);
    }

    /**
     * 列名转换成属性名
     *
     * @param name
     * @return
     */
    private String columnNameToFieldName(String name) {
        if (strategyConfig.getColumnNaming() == NamingStrategy.UNDERLINE_MUTUAL_CAMEL) {
            return ReplaceUtils.underlineToCamel(name);
        }

        return name;
    }

    /**
     * 表名转换成实体名
     *
     * @param name
     * @return
     */
    private String tableNameToEntityName(String name) {
        if (name.startsWith(strategyConfig.getTablePrefix())) {
            name = name.replaceFirst(strategyConfig.getTablePrefix(), "");
        }
        if (strategyConfig.getTableNaming() == NamingStrategy.UNDERLINE_MUTUAL_CAMEL) {
            return ReplaceUtils.capitalFirst(ReplaceUtils.underlineToCamel(name));
        }
        return name;
    }

    /**
     * 初始化实体列表
     */
    private void initEntityList() throws IOException, ClassNotFoundException {
        entityList = new ArrayList<>();
        //所有实体类
        List<String> classNameList = PackageScannerUtils.getClassNameList(packageConfig.getParent() + "." + packageConfig.getDomain());
        classNameList.addAll(PackageScannerUtils.getClassNameList(packageConfig.getOtherDomain()));
        for (String domain : classNameList) {
            Class<?> aClass = Class.forName(domain);
            if (aClass.isEnum() || aClass.isInterface() || aClass.isAnonymousClass() || aClass.getDeclaredFields().length == 0) {
                continue;
            }
            EntityInfo entityInfo = new EntityInfo();
            entityList.add(entityInfo);
            entityInfo.setName(aClass.getSimpleName());
            if (aClass.isAnnotationPresent(Table.class)) {
                Table table = aClass.getAnnotation(Table.class);
                entityInfo.setTableName(table.name());
            }
            entityInfo.setFields(FieldUtils.getAllFieldsList(aClass));
        }
    }


    /**
     * 读取数据库初始化tableList
     */
    private void initTableList() throws SQLException {
        tableList = new ArrayList<>();
        Connection connection = getConnection();
        if (connection == null) {
            throw new SQLException("数据库连接失败，请检查数据源");
        }
        String            tablesSql         = dataBaseQuery.tablesSql();
        PreparedStatement preparedStatement = connection.prepareStatement(tablesSql);
        ResultSet         results           = preparedStatement.executeQuery();
        TableInfo         tableInfo;
        while (results.next()) {
            String tableName = results.getString(dataBaseQuery.tableName());
            if (StringUtils.isNotEmpty(tableName)) {
                String tableComment = results.getString(dataBaseQuery.tableComment());
                //跳过视图
                if ("VIEW".equals(tableComment)) {
                    continue;
                }
                tableInfo = new TableInfo();
                tableInfo.setName(tableName);
                tableInfo.setComment(tableComment);
                initTableFields(tableInfo);
                tableList.add(tableInfo);
            } else {
                log.info("当前数据库没有表！！！");
            }
        }
    }

    /**
     * 初始化表字段
     *
     * @param tableInfo
     */
    private void initTableFields(TableInfo tableInfo) throws SQLException {
        Connection        connection        = getConnection();
        List<TableField>  fieldList         = new ArrayList<>();
        String            tableFieldsSql    = dataBaseQuery.tableFieldsSql(tableInfo.getName());
        PreparedStatement preparedStatement = connection.prepareStatement(tableFieldsSql);
        ResultSet         results           = preparedStatement.executeQuery();
        while (results.next()) {
            String     name  = results.getString(dataBaseQuery.fieldName());
            TableField field = new TableField();
            String     key   = results.getString(dataBaseQuery.fieldKey());
            //处理主键和自增
            if (StringUtils.isNotEmpty(key) && "PRI".equalsIgnoreCase(key)) {
                field.setKeyFlag(true);
                if (dataBaseQuery.isKeyIdentity(results)) {
                    field.setIdentityFlag(true);
                }
            }
            // 处理其它信息
            field.setName(name);
            field.setType(results.getString(dataBaseQuery.fieldType()));
            field.setComment(results.getString(dataBaseQuery.fieldComment()));
            fieldList.add(field);
        }
        tableInfo.setFields(fieldList);
    }

    /**
     * 得到数据库连接
     *
     * @return
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dataSourceConfig.getDataSource().getConnection();
        }
        return connection;
    }

    /**
     * 初始化数据库查询语句
     */
    private void init() {
        dataBaseQuery = new MySqlQuery();
        typeConvert = new MySqlTypeConvert();
        dataBaseUpdate = new MySqlUpdate(dataSourceConfig.getDataSource());
        packInfo = new PackInfo()
                .setEntityPack(packageConfig.getParent() + "." + packageConfig.getDomain())
                .setMapperPack(packageConfig.getParent() + "." + packageConfig.getMapper())
                .setServicePack(packageConfig.getParent() + "." + packageConfig.getService())
                .setServiceImplPack(packageConfig.getParent() + "." + packageConfig.getServiceImpl())
                .setControllerPack(packageConfig.getParent() + "." + packageConfig.getController());
    }
}
