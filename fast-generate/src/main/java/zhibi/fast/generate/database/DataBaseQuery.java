package zhibi.fast.generate.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataBaseQuery {
    /**
     * 查询全表的sql语句
     *
     * @return
     */
    String tablesSql();

    /**
     * 表名对应的结果列名
     *
     * @return
     */
    String tableName();

    /**
     * 表备注对应的结果列名
     *
     * @return
     */
    String tableComment();

    /**
     * 得到执笔表全部字段的sql
     *
     * @return
     */
    String tableFieldsSql(String tableName);

    /**
     * 查看是否是主键
     *
     * @return
     */
    String fieldKey();

    /**
     * 是否自增
     * @param results
     * @return
     */
    boolean isKeyIdentity(ResultSet results) throws SQLException;

    /**
     * 查看字段名
     * @return
     */
    String fieldName();

    /**
     * 查看字段类型名
     * @return
     */
    String fieldType();

    /**
     * 查看列备注名
     * @return
     */
    String fieldComment();
}
