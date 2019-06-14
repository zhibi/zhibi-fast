package zhibi.fast.generate.build;


import zhibi.fast.generate.config.GlobalConfig;
import zhibi.fast.generate.dto.FileOutPath;
import zhibi.fast.generate.dto.PackInfo;
import zhibi.fast.generate.engine.AbstractTemplateEngine;
import zhibi.fast.generate.engine.TemplateEngine;

/**
 * 自动生成文件
 *
 * @author 执笔
 * @date 2019/4/18 21:40
 */
public class FileGenerate {

    private Configuration configuration;
    private FileOutPath   fileOutPath;

    public FileGenerate(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 输出文件
     */
    public void execute() throws IllegalAccessException {
        init();
        AbstractTemplateEngine engine = new TemplateEngine(configuration, fileOutPath);
        engine.mkdirs().batchOutput().open();
    }

    /**
     * 初始化配置
     */
    private void init() {
        GlobalConfig globalConfig = configuration.getGlobalConfig();
        PackInfo     packInfo     = configuration.getPackInfo();
        fileOutPath = new FileOutPath()
                .setXmlPath(globalConfig.getOutputDir() + "\\" + packToDir(packInfo.getMapperPack()) + "\\" + "xml\\")
                .setEntityPath(globalConfig.getOutputDir() + "\\" + packToDir(packInfo.getEntityPack()) + "\\")
                .setServiceImplPath(globalConfig.getOutputDir() + "\\" + packToDir(packInfo.getServiceImplPack()) + "\\")
                .setMapperPath(globalConfig.getOutputDir() + "\\" + packToDir(packInfo.getMapperPack()) + "\\")
                .setServicePath(globalConfig.getOutputDir() + "\\" + packToDir(packInfo.getServicePack()) + "\\")
                .setControllerPath(globalConfig.getOutputDir() + "\\" + packToDir(packInfo.getControllerPack()) + "\\");
    }

    /**
     * 把包名转换成文件夹
     *
     * @return
     */
    private String packToDir(String pack) {
        return pack.replace(".", "//");
    }

}
