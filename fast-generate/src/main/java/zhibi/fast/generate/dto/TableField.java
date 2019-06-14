package zhibi.fast.generate.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 表字段信息
 */
@Data
@Accessors(chain = true)
public class TableField {

    private boolean keyFlag = false;
    private boolean identityFlag = false;
    private String name;
    private String type;
    private String comment;
}
