package zhibi.fast.generate.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 模板文件配置
 * @author 执笔
 */
@Data
@Accessors(chain = true)
public class TemplateConfig {

    private String entity = "/templates/entity.java";
    private String service = "/templates/service.java";
    private String serviceImpl = "/templates/serviceImpl.java";
    private String mapper = "/templates/mapper.java";
    private String xml = "/templates/mapper.xml";
    private String controller = "/templates/controller.java";


}
