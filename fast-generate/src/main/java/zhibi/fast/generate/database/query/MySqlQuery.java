package zhibi.fast.generate.database.query;


import zhibi.fast.generate.database.DataBaseQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlQuery implements DataBaseQuery {
    @Override
    public String tablesSql() {
        return "SHOW TABLE STATUS";
    }

    @Override
    public String tableName() {
        return "NAME";
    }

    @Override
    public String tableComment() {
        return "COMMENT";
    }

    @Override
    public String tableFieldsSql(String tableName) {
        return "SHOW FULL FIELDS FROM " + tableName;
    }

    @Override
    public String fieldKey() {
        return "KEY";
    }

    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return "auto_increment".equals(results.getString("Extra"));
    }

    @Override
    public String fieldName() {
        return "FIELD";
    }

    @Override
    public String fieldType() {
        return "TYPE";
    }

    @Override
    public String fieldComment() {
        return "COMMENT";
    }
}
