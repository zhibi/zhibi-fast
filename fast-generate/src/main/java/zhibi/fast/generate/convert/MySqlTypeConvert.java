package zhibi.fast.generate.convert;

import zhibi.fast.generate.enums.ColumnType;

/**
 * @author 执笔
 */
public class MySqlTypeConvert implements TypeConvert {
    @Override
    public ColumnType processByFieldType(String type) {
        String t = type.toUpperCase();
        if (t.contains("STRING")) {
            return ColumnType.STRING;
        } else if (t.contains("LONG")) {
            return ColumnType.LONG;
        } else if (t.contains("BOOLEAN")) {
            return ColumnType.BOOLEAN;
        } else if (t.contains("INTEGER")) {
            return ColumnType.INTEGER;
        } else if (t.contains("BIGDECIMAL")) {
            return ColumnType.BIG_DECIMAL;
        } else if (t.contains("FLOAT")) {
            return ColumnType.FLOAT;
        } else if (t.contains("DOUBLE")) {
            return ColumnType.DOUBLE;
        } else if (t.contains("DATE")) {
            return ColumnType.DATE;
        }
        return ColumnType.STRING;
    }

    @Override
    public ColumnType processByColumnType(String type) {
        String t = type.toLowerCase();
        if (t.contains("char")) {
            return ColumnType.STRING;
        } else if (t.contains("bigint")) {
            return ColumnType.LONG;
        } else if (t.contains("tinyint(1)")) {
            return ColumnType.BOOLEAN;
        } else if (t.contains("int")) {
            return ColumnType.INTEGER;
        } else if (t.contains("text")) {
            return ColumnType.TEXT;
        } else if (t.contains("bit")) {
            return ColumnType.BOOLEAN;
        } else if (t.contains("decimal")) {
            return ColumnType.BIG_DECIMAL;
        } else if (t.contains("clob")) {
            return ColumnType.CLOB;
        } else if (t.contains("blob")) {
            return ColumnType.BLOB;
        } else if (t.contains("binary")) {
            return ColumnType.BYTE_ARRAY;
        } else if (t.contains("float")) {
            return ColumnType.FLOAT;
        } else if (t.contains("double")) {
            return ColumnType.DOUBLE;
        } else if (t.contains("json") || t.contains("enum")) {
            return ColumnType.STRING;
        } else if (t.contains("date") || t.contains("time") || t.contains("year")) {
            return ColumnType.DATE;
        }
        return ColumnType.STRING;
    }
}
