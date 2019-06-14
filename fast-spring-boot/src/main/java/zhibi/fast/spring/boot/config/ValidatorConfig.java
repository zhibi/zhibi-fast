package zhibi.fast.spring.boot.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author 执笔
 * @date 2018/12/28 22:47
 */
@Configuration
@ConditionalOnClass({HibernateValidator.class, Validator.class})
public class ValidatorConfig {

    /**
     * 快速返回模式
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(Validator.class)
    public Validator getValidator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
