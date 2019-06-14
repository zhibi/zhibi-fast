package zhibi.fast.generate.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import zhibi.fast.generate.enums.ColumnType;

/**
 * 列和实体属性
 * @author 执笔
 * @date 2019/4/18 22:32
 */
@Data
@Accessors(chain = true)
public class ColumnField {

    /**
     * 时候主键
     */
    private boolean    keyFlag      = false;
    /**
     * 是否自增
     */
    private boolean    identityFlag = false;
    /**
     * 列名
     */
    private String     columnName;
    /**
     * 属性名
     */
    private String     fieldName;
    /**
     * 属性类型
     */
    private String     fieldType;
    /**
     * 列类型
     */
    private ColumnType columnType;
    /**
     * 备注
     */
    private String     comment;
}
