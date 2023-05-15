package cn.devcxl.common.annotation.switchs;

import cn.devcxl.common.aop.RequestDebugLogAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启@WebLog注解实现记录请求
 *
 * @author devcxl
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(RequestDebugLogAspect.class)
public @interface EnableRequestDebugLog {
}
