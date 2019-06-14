package zhibi.fast.spring.boot.engine.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import zhibi.fast.spring.boot.engine.TemplateEngine;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

/**
 * @author 执笔
 * Freemarker 模板引擎
 */
@Slf4j
public class FreemarkerTemplateEngine implements TemplateEngine {
    private Configuration configuration;

    /**
     * @param pathPrefix 模板前缀
     */
    public FreemarkerTemplateEngine(String pathPrefix) {
        configuration = new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(FreemarkerTemplateEngine.class, pathPrefix);
    }

    @Override
    public void process(String template, OutputStream outputStream, Map<String, Object> data) {
        try {
            Template ftl = configuration.getTemplate(template);
            ftl.process(data, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            outputStream.close();
        } catch (IOException | TemplateException e) {
            log.error("【模板生成失败】" + e);
        }
    }

    @Override
    public String process(String template, Map<String, Object> data) {
        try {
            Template ftl = configuration.getTemplate(template);
            StringWriter writer = new StringWriter();
            ftl.process(data, writer);
            writer.flush();
            return writer.toString();
        } catch (IOException | TemplateException e) {
            log.error("【模板生成失败】" + e);
        }
        return "";
    }
}
