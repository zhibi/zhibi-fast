package zhibi.fast.generate.build;


import zhibi.fast.generate.convert.TypeConvert;
import zhibi.fast.generate.database.DataBaseUpdate;
import zhibi.fast.generate.dto.ColumnField;
import zhibi.fast.generate.dto.TableEntity;
import zhibi.fast.generate.dto.TableField;
import zhibi.fast.generate.dto.TableInfo;
import zhibi.fast.generate.enums.ColumnType;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自动更新数据库
 *
 * @author 执笔
 * @date 2019/4/18 23:01
 */
public class DataBaseGenerate {

    private Configuration configuration;

    private DataBaseUpdate dataBaseUpdate;

    public DataBaseGenerate(Configuration configuration) {
        this.configuration = configuration;
    }

    public void execute() {
        dataBaseUpdate = configuration.getDataBaseUpdate();
        List<TableEntity> tableEntityList = configuration.getTableEntityList();
        List<TableInfo>   tableList       = configuration.getTableList();
        tableEntityList.forEach(tableEntity -> {
            String        tableName = tableName(tableEntity.getTableName());
            AtomicBoolean flag      = new AtomicBoolean(false);
            tableList.stream()
                    .filter(item -> tableName.equalsIgnoreCase(tableName(item.getName())))
                    .forEach(item -> {
                        flag.set(true);//去处理字段
                        checkTableField(item, tableEntity);
                    });
            if (!flag.get()) dataBaseUpdate.createTable(tableEntity);
        });
    }

    /**
     * 处理字段
     *
     * @param tableInfo
     * @param tableEntity
     */
    private void checkTableField(TableInfo tableInfo, TableEntity tableEntity) {
        List<ColumnField> columnFields = tableEntity.getColumnFields();
        List<TableField>  fields       = tableInfo.getFields();
        for (ColumnField columnField : columnFields) {
            String                      columnName = columnField.getColumnName();
            AtomicBoolean               flag       = new AtomicBoolean(false);
            AtomicReference<TableField> tableField = new AtomicReference<>(null);
            fields.stream().filter(item -> columnName.equalsIgnoreCase(item.getName()))
                    .forEach(item -> {
                        flag.set(true);
                        tableField.set(item);
                        checkFieldType(tableInfo, columnField, item);
                    });
            if (!flag.get()) {
                dataBaseUpdate.addColumn(tableInfo.getName(), columnField);
            } else {
                fields.remove(tableField.get());
            }
        }
        //删除多余字段
        if (configuration.getDataSourceConfig().getDelOverColumn()) {
            fields.forEach(item -> dataBaseUpdate.deleteColumn(tableInfo.getName(), item.getName()));
        }

    }

    /**
     * 校验列类型
     *
     * @param tableInfo
     * @param columnField
     * @param item
     */
    private void checkFieldType(TableInfo tableInfo, ColumnField columnField, TableField item) {
        TypeConvert typeConvert = configuration.getTypeConvert();
        ColumnType  columnType  = columnField.getColumnType();
        ColumnType  tableType   = typeConvert.processByColumnType(item.getType());
        if (columnType != tableType) {
            dataBaseUpdate.modifyColumn(tableInfo.getName(), columnField);
        }
    }

    /**
     * 替换掉表格前缀
     *
     * @param name
     * @return
     */
    private String tableName(String name) {
        if (name.startsWith(configuration.getStrategyConfig().getTablePrefix())) {
            name = name.replaceFirst(configuration.getStrategyConfig().getTablePrefix(), "");
        }
        return name;
    }
}
