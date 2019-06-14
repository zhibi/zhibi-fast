package zhibi.fast.generate.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zhibi.fast.generate.AutoGenerate;
import zhibi.fast.generate.config.DataSourceConfig;

import javax.sql.DataSource;

/**
 * 自动配置
 *
 * @author 执笔
 * @date 2019/4/18 22:48
 */
@Configuration
@EnableConfigurationProperties(GeneratorProperties.class)
public class GenerateAutoConfiguration {

    @Autowired
    private GeneratorProperties generatorProperties;
    @Autowired
    private DataSource          dataSource;

    @Bean
    public AutoGenerate autoGenerate() throws Exception {
        DataSourceConfig dataSourceConfig = generatorProperties.getDataSource();
        if (dataSourceConfig == null) {
            dataSourceConfig = new DataSourceConfig();
        }
        dataSourceConfig.setDataSource(dataSource);
        AutoGenerate autoGenerate = new AutoGenerate(dataSourceConfig,
                generatorProperties.getPackageConfig(),
                generatorProperties.getGlobal(),
                generatorProperties.getStrategy(),
                generatorProperties.getTemplate());
        autoGenerate.execute();
        return autoGenerate;
    }
}
