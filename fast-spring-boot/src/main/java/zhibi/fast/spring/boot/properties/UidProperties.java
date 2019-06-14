package zhibi.fast.spring.boot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 执笔
 * @date 2019/1/22 16:39
 */
@Data
@Component
@ConfigurationProperties(prefix = "fast.uid")
public class UidProperties {
    /**
     * uid 的 机器ID
     */
    private Integer workerId = 8;

    /**
     * uid 的 序列
     */
    private Integer sequence = 8;


}
