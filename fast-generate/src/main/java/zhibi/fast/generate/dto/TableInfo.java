package zhibi.fast.generate.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 数据表信息
 *
 * @author 执笔
 * @date 2019/4/18 21:35
 */
@Data
@Accessors(chain = true)
public class TableInfo {

    /**
     * 表名
     */
    private String           name;
    /**
     * 备注
     */
    private String           comment;
    /**
     * 所有的列
     */
    private List<TableField> fields;
}
