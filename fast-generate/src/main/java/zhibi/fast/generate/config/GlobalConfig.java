package zhibi.fast.generate.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 全局配置
 *
 * @author 执笔
 */
@Data
@Accessors(chain = true)
public class GlobalConfig {
    /**
     * 生成文件的输出目录【默认 D 盘根目录】
     */
    private String outputDir = "D://";

    /**
     * 是否覆盖已有文件
     */
    private boolean fileOverride = true;

    /**
     * 是否打开输出目录
     */
    private boolean open = false;

    /**
     * 是否需要自动生成文件
     */
    private boolean generatorFile = true;

    /**
     * 开发人员
     */
    private String author = "执笔";

    /**
     * 是否启用
     */
    private boolean enable = false;


    /**
     * 各层文件名称方式，例如： %Action 生成 UserAction
     */
    private String mapperName      = "%sMapper";
    private String xmlName         = "%sMapper";
    private String serviceName     = "%sService";
    private String serviceImplName = "%sServiceImpl";
    private String controllerName  = "%sController";

}
