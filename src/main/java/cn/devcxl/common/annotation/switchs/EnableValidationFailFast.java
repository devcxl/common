package cn.devcxl.common.annotation.switchs;


import cn.devcxl.common.config.ParameterValidationConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启@Validated快速失败
 * @author devcxl
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ParameterValidationConfig.class)
public @interface EnableValidationFailFast {
}
