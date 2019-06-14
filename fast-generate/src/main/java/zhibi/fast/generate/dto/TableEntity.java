package zhibi.fast.generate.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 数据表和实体综合
 *
 * @author 执笔
 * @date 2019/4/18 21:32
 */
@Data
@Accessors(chain = true)
public class TableEntity {

    /**
     * 表名
     */
    private String tableName;
    /**
     * 实体名
     */
    private String entityName;
    /**
     * 备注
     */
    private String comment;
    /**
     * mapper 的名字
     */
    private String mapperName;
    /**
     * mapper xml 的名字
     */
    private String xmlName;
    /**
     * service 的名字
     */
    private String serviceName;
    /**
     * service 实现类的名字
     */
    private String serviceImplName;
    /**
     * controller 的名字
     */
    private String controllerName;
    /**
     * 所有的属性
     */
    private List<ColumnField> columnFields;
}
