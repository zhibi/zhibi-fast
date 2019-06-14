package zhibi.fast.generate.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文件输出的路径信息
 *
 * @author 执笔
 * @date 2019/4/18 21:20
 */
@Data
@Accessors(chain = true)
public class FileOutPath {

    /**
     * 实体类的路径
     */
    private String entityPath;
    /**
     * mapper的路径
     */
    private String mapperPath;
    /**
     * mapper xml 的路径
     */
    private String xmlPath;
    /**
     * service的路径
     */
    private String servicePath;
    /**
     * service 实现类的路径
     */
    private String serviceImplPath;
    /**
     * controller的路径
     */
    private String controllerPath;
}
