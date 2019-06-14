package zhibi.fast.generate.database;

import zhibi.fast.generate.dto.ColumnField;
import zhibi.fast.generate.dto.TableEntity;

public interface DataBaseUpdate {

    /**
     * 创建表格
     *
     * @param tableEntity
     */
    void createTable(TableEntity tableEntity);

    /**
     * 添加一列
     *
     * @param tableName
     * @param columnField
     */
    void addColumn(String tableName, ColumnField columnField);

    /**
     * 修改一列
     *
     * @param columnField
     */
    void modifyColumn(String tableName, ColumnField columnField);

    /**
     * 删除一列
     *
     * @param name
     */
    void deleteColumn(String tableName, String name);
}
