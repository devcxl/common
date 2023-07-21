package cn.devcxl.common.config;


import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Spring Validation校验机制默认会校验完所有字段，然后才抛出异常。
 * 但是，在某些情况下，我们希望出现一个校验错误就立马返回。
 * 如果想要达成这种效果，需要通过配置开启 Fali Fast 机制，一旦校验失败就立即返回。
 * @author devcxl
 */
public class ParameterValidationConfig {
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 开启 fail fast 机制
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}