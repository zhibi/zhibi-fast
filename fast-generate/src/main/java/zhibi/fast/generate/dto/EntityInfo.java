package zhibi.fast.generate.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 实体信息
 * @author 执笔
 * @date 2019/4/18 21:34
 */
@Data
@Accessors(chain = true)
public class EntityInfo {

    /**
     * 名字
     */
    private String name;
    /**
     * 表名
     */
    private String tableName;

    /**
     * 所有的属性
     */
    private List<Field> fields;
}
