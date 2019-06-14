package zhibi.fast.spring.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import zhibi.fast.spring.boot.freemarker.CustomObjectWrapper;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

/**
 * @author 执笔
 * @date 2019/4/20 22:42
 */
@Configuration
@ConditionalOnClass({FreeMarkerConfigurer.class})
@ConditionalOnBean(FreeMarkerConfig.class)
public class FreemarkerConfig {
    /**
     * 配置freemarker 支持 java8 时间
     *
     * @param configurer
     */
    @Autowired
    private void configureFreemarkerConfigurer(FreeMarkerConfig configurer) {
        configurer.getConfiguration().setObjectWrapper(new CustomObjectWrapper(DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
    }
}
