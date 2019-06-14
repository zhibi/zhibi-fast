package zhibi.fast.spring.boot.properties;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;

/**
 * @author 执笔
 * @date 2019/1/22 16:39
 */
@Data
@Component
@ConfigurationProperties(prefix = "fast.upload")
public class UploadProperties {

    @Autowired
    private ServletContext servletContext;

    /**
     * 上传文件的路径
     */
    private String path = "";

    /**
     * 上传文件的路径前缀
     */
    private String prefix = "";

    public String getPath() {
        if (StringUtils.isBlank(path)) {
            try {
                return ResourceUtils.getURL("classpath:").getPath() + "public";
            } catch (FileNotFoundException e) {
                return servletContext.getRealPath("/") + "public";
            }
        }
        return path;
    }
}
