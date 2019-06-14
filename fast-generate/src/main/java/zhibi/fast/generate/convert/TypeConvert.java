package zhibi.fast.generate.convert;


import zhibi.fast.generate.enums.ColumnType;

public interface TypeConvert {
    /**
     * 通过属性类型转换
     *
     * @param type
     * @return
     */
    ColumnType processByFieldType(String type);

    /**
     * 通过数据库列类型转换
     *
     * @param type
     * @return
     */
    ColumnType processByColumnType(String type);
}
