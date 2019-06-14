package zhibi.fast.generate.config;

import lombok.Data;
import lombok.experimental.Accessors;
import zhibi.fast.generate.enums.NamingStrategy;
import zhibi.fast.generate.enums.SyncStrategy;

/**
 * 策略配置项
 *
 * @author 执笔
 * @date 2019/4/18 22:07
 */
@Data
@Accessors(chain = true)
public class StrategyConfig {

    /**
     * 数据库和实体名字的命名策略
     */
    private NamingStrategy tableNaming           = NamingStrategy.UNDERLINE_MUTUAL_CAMEL;
    /**
     * 数据库表字段映射和实体属性的命名策略
     */
    private NamingStrategy columnNaming          = NamingStrategy.UNDERLINE_MUTUAL_CAMEL;
    /**
     * 同步策略
     */
    private SyncStrategy   sync                  = SyncStrategy.ENTITY_TO_DATABASE;
    /**
     * 自定义继承的Entity类全称，带包名
     */
    private String         superEntityClass;
    /**
     * 自定义继承的Mapper类全称，带包名
     */
    private String         superMapperClass      = "zhibi.fast.mybatis.mapper.BaseMapper";
    /**
     * 自定义继承的Service类全称，带包名
     */
    private String         superServiceClass     = "zhibi.fast.mybatis.service.BaseService";
    /**
     * 自定义继承的ServiceImpl类全称，带包名
     */
    private String         superServiceImplClass = "zhibi.fast.mybatis.service.impl.BaseServiceImpl";
    /**
     * 自定义继承的Controller类全称，带包名
     */
    private String         superControllerClass  = "zhibi.fast.spring.boot.controller.BaseController";

    /**
     * 数据库的表前缀
     * 生成实体时候会去掉
     */
    private String tablePrefix = "t_";

}
