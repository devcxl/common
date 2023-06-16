package cn.devcxl.common.interceptor;

import cn.devcxl.common.annotation.CommonAuth;
import cn.devcxl.common.annotation.CommonNoAuth;
import cn.devcxl.common.exception.UnauthorizedException;
import cn.devcxl.common.utils.JsonWebTokenUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author devcxl
 * 认证拦截
 */
public abstract class CommonAuthInterceptor implements HandlerInterceptor {


    /**
     * 请求头中的Token名称
     */
    String TOKEN_NAME = "token";
    private RedisTemplate<String, Object> redisTemplate;
    private JsonWebTokenUtils jsonWebTokenUtils;

    public CommonAuthInterceptor(RedisTemplate<String, Object> redisTemplate, JsonWebTokenUtils jsonWebTokenUtils) {
        this.redisTemplate = redisTemplate;
        this.jsonWebTokenUtils = jsonWebTokenUtils;
    }

    /**
     * 检查token判断是否放行
     *
     * @return
     */
    public abstract boolean checkPermission(String token, String auth);

    /**
     * default preHandle
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 存在NoAuth注解的无需认证
            CommonNoAuth annotation = method.getAnnotation(CommonNoAuth.class);
            if (annotation != null) {
                return true;
            }
            String token = request.getHeader(TOKEN_NAME);


            if (StringUtils.hasLength(token)) {
                if (jsonWebTokenUtils.validateToken(token)) {
                    // 权限认证
                    CommonAuth commonAuth = method.getAnnotation(CommonAuth.class);
                    if (commonAuth != null) {
                        return checkPermission(token, commonAuth.value());
                    } else {
                        return true;
                    }
                } else {
                    throw new UnauthorizedException();
                }
            }
            throw new UnauthorizedException();
        } else {
            return false;
        }
    }
}
