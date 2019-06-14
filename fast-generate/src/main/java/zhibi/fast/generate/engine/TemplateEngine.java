package zhibi.fast.generate.engine;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import zhibi.fast.generate.dto.FileOutPath;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author 执笔
 * @date 2019/4/18 21:09
 */
@Slf4j
public class TemplateEngine extends AbstractTemplateEngine {

    private Configuration configuration;

    /**
     * <p>
     * 模板引擎初始化
     * </p>
     *
     * @param config
     * @param fileOutPath
     */
    public TemplateEngine(zhibi.fast.generate.build.Configuration config, FileOutPath fileOutPath) {
        super(config, fileOutPath);
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(TemplateEngine.class, "/");
    }

    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        Template         template         = configuration.getTemplate(templatePath);
        FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFile));
        template.process(objectMap, new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
        fileOutputStream.close();
        log.info("※生成※  模板：[{}]  文件：[{}]", templatePath, outputFile);
    }


    @Override
    public String templateFilePath(String filePath) {
        return filePath + ".ftl";
    }
}
