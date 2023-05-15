package cn.devcxl.common.exception.annotation;

import cn.devcxl.common.exception.handler.GlobalExceptionHandler;
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
@Import(GlobalExceptionHandler.class)
public @interface EnableGlobalExceptionHandler {
}
