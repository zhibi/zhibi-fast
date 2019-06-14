package zhibi.fast.generate.database.update;


import lombok.extern.slf4j.Slf4j;
import zhibi.fast.generate.database.DataBaseUpdate;
import zhibi.fast.generate.dto.ColumnField;
import zhibi.fast.generate.dto.TableEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * mysql 更新数据库语句
 *
 * @author 执笔
 * @date 2019/4/18 21:47
 */
@Slf4j
public class MySqlUpdate implements DataBaseUpdate {

    private DataSource dataSource;
    private Connection connection;

    public MySqlUpdate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void createTable(TableEntity tableEntity) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ").append(tableEntity.getTableName()).append("( ");
        for (ColumnField columnField : tableEntity.getColumnFields()) {
            TypeConvert typeConvert = toTypeConvert(columnField.getColumnType().name());
            sql.append("`").append(columnField.getColumnName()).append("`")
                    .append(" ").append(typeConvert.getSqlType());
            if (columnField.isIdentityFlag()) {
                sql.append(" AUTO_INCREMENT ");
            } else {
                sql.append(" DEFAULT ").append(typeConvert.getDefaultVal());
            }
            sql.append(",");
        }
        sql.append("PRIMARY KEY (");
        boolean first = true;
        for (ColumnField columnField : tableEntity.getColumnFields()) {
            if (columnField.isKeyFlag()) {
                if (!first) {
                    sql.append(",");
                } else {
                    first = false;
                }
                sql.append("`").append(columnField.getColumnName()).append("`");
            }
        }
        sql.append(") ) ENGINE = InnoDB AUTO_INCREMENT = 1000000");
        executeSql(sql.toString());
    }

    @Override
    public void addColumn(String tableName, ColumnField columnField) {
        TypeConvert typeConvert = toTypeConvert(columnField.getColumnType().name().toUpperCase());
        String sql = "ALTER TABLE " + tableName +
                " ADD COLUMN `" + columnField.getColumnName() + "`" +
                " " + typeConvert.getSqlType() + " DEFAULT " +
                typeConvert.getDefaultVal();
        executeSql(sql);
        if (columnField.isKeyFlag()) {
            sql = "ALTER TABLE " + tableName +
                    " ADD primary key(" + columnField.getColumnName() + ")";
            executeSql(sql);
            if (columnField.isIdentityFlag()) {
                sql = "ALTER TABLE " + tableName +
                        " modify `" + columnField.getColumnName() + "`" +
                        " " + typeConvert.getSqlType() + " AUTO_INCREMENT";
                executeSql(sql);
            }
        }
    }

    @Override
    public void modifyColumn(String tableName, ColumnField columnField) {
        TypeConvert typeConvert = toTypeConvert(columnField.getColumnType().name().toUpperCase());
        String      sql         = "ALTER TABLE `" + tableName + "` MODIFY COLUMN `" + columnField.getColumnName() + "` " + typeConvert.getSqlType() + " DEFAULT " + typeConvert.getDefaultVal();
        executeSql(sql);
    }

    @Override
    public void deleteColumn(String tableName, String name) {
        executeSql("ALTER TABLE `" + tableName + "` DROP COLUMN `" + name + "`");
    }

    private void executeSql(String sql) {
        try {
            getConnection().prepareStatement(sql).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            log.info("→执行← SQL:{}  ", sql);
        }
    }

    private Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 转换类型
     *
     * @return
     */
    private static TypeConvert toTypeConvert(String type) {
        try {
            return TypeConvert.valueOf(type);
        } catch (Exception e) {
            return TypeConvert.STRING;
        }
    }

    public enum TypeConvert {
        INTEGER("int", 0),
        LONG("bigint", 0),
        STRING("varchar(255)", "''"),
        DATE("datetime", null),
        BIG_DECIMAL("decimal(65, 2)", 0),
        DOUBLE("double(255,2)", 0),
        TEXT("text", null),
        BOOLEAN("tinyint(1)", 0);

        private String sqlType;
        private Object defaultVal;

        TypeConvert(String sqlType, Object defaultVal) {
            this.sqlType = sqlType;
            this.defaultVal = defaultVal;
        }

        public String getSqlType() {
            return sqlType;
        }

        public Object getDefaultVal() {
            return defaultVal;
        }
    }
}
