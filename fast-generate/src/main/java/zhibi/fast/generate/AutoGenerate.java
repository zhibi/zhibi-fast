package zhibi.fast.generate;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zhibi.fast.generate.build.Configuration;
import zhibi.fast.generate.build.DataBaseGenerate;
import zhibi.fast.generate.build.FileGenerate;
import zhibi.fast.generate.config.*;

/**
 * 自动生成类
 *
 * @author 执笔
 * @date 2019/4/18 21:36
 */
@AllArgsConstructor
@Slf4j
public class AutoGenerate {

    private DataSourceConfig dataSourceConfig;
    private PackageConfig    packageConfig;
    private GlobalConfig     globalConfig;
    private StrategyConfig   strategyConfig;
    private TemplateConfig   templateConfig;

    /**
     * 开始执行
     */
    public void execute() throws Exception {
        Configuration configuration = new Configuration(dataSourceConfig, packageConfig, globalConfig, strategyConfig, templateConfig);
        if (configuration.getGlobalConfig().isEnable()) {
            if (configuration.getGlobalConfig().isGeneratorFile()) {
                log.info("〓〓〓〓〓〓〓〓〓...准备生成文件...〓〓〓〓〓〓〓〓〓");
                new FileGenerate(configuration).execute();
                log.info("〓〓〓〓〓〓〓〓〓...文件生成完成...〓〓〓〓〓〓〓〓〓");
            }
            log.info("〓〓〓〓〓〓〓〓〓...准备同步数据库...〓〓〓〓〓〓〓〓〓");
            new DataBaseGenerate(configuration).execute();
            log.info("〓〓〓〓〓〓〓〓〓...数据库同步完成...〓〓〓〓〓〓〓〓〓");
        }
    }
}
