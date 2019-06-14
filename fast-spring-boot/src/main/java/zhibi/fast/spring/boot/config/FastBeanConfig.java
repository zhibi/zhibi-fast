package zhibi.fast.spring.boot.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import zhibi.fast.commons.uid.UidGenerator;
import zhibi.fast.commons.uid.impl.DefaultUidGenerator;
import zhibi.fast.spring.boot.properties.UidProperties;

import java.text.ParseException;
import java.util.Date;

/**
 * 配置需要加载的bean
 *
 * @author 执笔
 * @date 2018/12/28 22:46
 */
@Configuration
public class FastBeanConfig {

    @Autowired
    private UidProperties uidProperties;

    /**
     * ID生成器
     *
     * @return
     */
    @Bean
    @ConditionalOnClass(UidGenerator.class)
    @ConditionalOnMissingBean(UidGenerator.class)
    public UidGenerator uidGenerator() {
        return new DefaultUidGenerator(uidProperties.getWorkerId(), uidProperties.getSequence());
    }

    /**
     * springboot 接收时间类型处理
     *
     * @return
     */
    @Bean
    public Converter<String, Date> dateConverter() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                if (StringUtils.isEmpty(source))
                    return null;
                else {
                    try {
                        return DateUtils.parseDate(source, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
    }

}
