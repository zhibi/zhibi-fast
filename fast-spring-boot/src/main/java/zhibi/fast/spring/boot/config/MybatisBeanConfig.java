package zhibi.fast.spring.boot.config;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zhibi.fast.mybatis.interceptor.UpdateDateInterceptor;
import zhibi.fast.mybatis.interceptor.UpdateOperationInterceptor;

/**
 * @author 执笔
 * @date 2019/4/17 14:26
 */
@Configuration
@ConditionalOnClass(Interceptor.class)
public class MybatisBeanConfig {

    /**
     * mybatis更新数据库时间插件
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UpdateDateInterceptor.class)
    public Interceptor updateDateInterceptor() {
        return new UpdateDateInterceptor();
    }

    /**
     * 自动更新操作人的插件
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UpdateOperationInterceptor.class)
    public Interceptor updateOperationInterceptor() {
        return new UpdateOperationInterceptor();
    }


}
