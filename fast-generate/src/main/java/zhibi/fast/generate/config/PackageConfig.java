package zhibi.fast.generate.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 生成类相关包的配置
 * @author 执笔
 */
@Data
@Accessors(chain = true)
public class PackageConfig {

    /**
     * 父包名
     */
    private String parent = "zhibi";

    /**
     * domain包名
     */
    private String domain = "domain";

    /**
     * Service包名
     */
    private String service = "service";

    /**
     * Service Impl包名
     */
    private String serviceImpl = "service.impl";

    /**
     * Mapper包名
     */
    private String mapper = "mapper";

    /**
     * Mapper XML包名
     */
    private String xml = "mapper.xml";

    /**
     * Controller包名
     */
    private String controller = "controller";

    /**
     * 其它实体类包名
     * 多个用,隔开
     * 用于生成数据库
     * 用全包名
     */
    public String otherDomain;
}
