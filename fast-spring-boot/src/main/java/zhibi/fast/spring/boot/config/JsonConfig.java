package zhibi.fast.spring.boot.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import zhibi.fast.commons.json.CustomerDateFormat;

import java.text.DateFormat;

/**
 * @author 执笔
 * @date 2019/4/23 12:30
 */
@Configuration
public class JsonConfig {


    @Autowired
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = jackson2ObjectMapperBuilder.build();
        // 对应不上的字段自动忽略
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 不加双引号也可以使用
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        DateFormat dateFormat = objectMapper.getDateFormat();
        objectMapper.setDateFormat(new CustomerDateFormat(dateFormat, "yyyy-MM-dd HH:mm:ss"));
        return objectMapper;
    }
}
