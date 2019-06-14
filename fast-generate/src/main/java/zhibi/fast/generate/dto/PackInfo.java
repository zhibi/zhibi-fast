package zhibi.fast.generate.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 生成类的包名
 * @author 执笔
 * @date 2019/4/18 21:22
 */
@Data
@Accessors(chain = true)
public class PackInfo {

    /**
     * 实体类的包名
     */
    private String entityPack;
    /**
     * mapper的包名
     */
    private String mapperPack;
    /**
     * service 的包名
     */
    private String servicePack;
    /**
     * service impl 的包名
     */
    private String serviceImplPack;
    /**
     * controller 的包名
     */
    private String controllerPack;
}
