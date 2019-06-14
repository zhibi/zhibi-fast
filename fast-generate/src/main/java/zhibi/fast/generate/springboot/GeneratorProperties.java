package zhibi.fast.generate.springboot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import zhibi.fast.generate.config.*;

/**
 *
 * @author 执笔
 * @date 2019/4/18 22:47
 */
@Data
@ConfigurationProperties(prefix = "fast.generate")
@Component
public class GeneratorProperties {

    private GlobalConfig     global;
    private TemplateConfig   template;
    private DataSourceConfig dataSource;
    private StrategyConfig   strategy;
    private PackageConfig    packageConfig;

    public PackageConfig getPackage() {
        return packageConfig;
    }

    public void setPackage(PackageConfig packageConfig) {
        this.packageConfig = packageConfig;
    }
}
