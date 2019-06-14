package zhibi.fast.spring.boot.engine;

import java.io.OutputStream;
import java.util.Map;

/**
 * @author 执笔
 * 模板引擎
 */
public interface TemplateEngine {

    /**
     * 根据模板输出
     *
     * @param template     模板文件 当前classpath路径下面
     * @param outputStream 输出流
     * @param data         数据
     */
    void process(String template, OutputStream outputStream, Map<String, Object> data);

    /**
     * 根据模板得到字符串
     *
     * @param template 模板文件 当前classpath路径下面
     * @param data     数据
     * @return
     */
    String process(String template, Map<String, Object> data);
}
