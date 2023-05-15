package cn.devcxl.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求日志
 *
 * @author devcxl
 */
@Slf4j
@Aspect
@Profile({"test", "dev"})
public class RequestDebugLogAspect {

    @Pointcut("@annotation(cn.devcxl.common.annotation.RequestDebug)")
    public void requestLog() {
    }

    @Before("requestLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // 记录下请求内容
            log.info("[Request] ip: {}", request.getRemoteAddr());
            log.info("[Request] method: {} url:{}", request.getMethod(), request.getRequestURL());
            log.info("[Request] param: {} {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(), joinPoint.getArgs());
        }
    }

    @AfterReturning(returning = "ret", pointcut = "requestLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("[Response]: {}", ret);
    }

}
