package cn.devcxl.common.config;

import cn.devcxl.common.interceptor.CommonAuthInterceptor;
import cn.devcxl.common.utils.JsonWebTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author devcxl
 */
@Slf4j
@Component
public class CommonAuthInterceptorImpl extends CommonAuthInterceptor {

    public CommonAuthInterceptorImpl(RedisTemplate<String, Object> redisTemplate, JsonWebTokenUtils jsonWebTokenUtils) {
        super(redisTemplate, jsonWebTokenUtils);
    }

    @Override
    public boolean checkPermission(String token, String auth) {
        return false;
    }
}
