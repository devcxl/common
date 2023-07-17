package cn.devcxl.common.exception.annotation;

import cn.devcxl.common.exception.controller.CustomErrorController;
import cn.devcxl.common.exception.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启全局错误处理 以及自定义错误接口
 *
 * @author devcxl
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({GlobalExceptionHandler.class, CustomErrorController.class})
public @interface EnableGlobalExceptionHandler {
}
