package cn.devcxl.common.annotation.switchs;

import cn.devcxl.common.config.security.JsonWebTokenConfig;
import cn.devcxl.common.config.security.SecurityIgnoreUrlsProperties;
import cn.devcxl.common.utils.JsonWebTokenUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author devcxl
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ComponentScan("cn.devcxl.common.component")
@Import({JsonWebTokenConfig.class, JsonWebTokenUtils.class})
@EnableConfigurationProperties(SecurityIgnoreUrlsProperties.class)
public @interface EnableSecurityConfig {
}
