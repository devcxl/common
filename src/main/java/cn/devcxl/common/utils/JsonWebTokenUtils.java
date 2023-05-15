package cn.devcxl.common.utils;

import cn.devcxl.common.config.security.JsonWebTokenConfig;
import cn.devcxl.common.constant.JwtConstant;
import cn.hutool.jwt.JWTUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author devcxl
 */
@Component
public class JsonWebTokenUtils {

    @Resource
    private JsonWebTokenConfig jsonWebTokenConfig;

    public String getUserNameFromToken(String authToken) {
        return JWTUtil.parseToken(authToken).getPayloads().getStr(JwtConstant.USERNAME);
    }

    /**
     * 验证token是否有效&是否过期
     *
     * @param authToken
     * @return
     */
    public boolean validateToken(@NotBlank String authToken) {
        if (JWTUtil.verify(authToken, jsonWebTokenConfig.getJWTSigner())) {
            Long exp = JWTUtil.parseToken(authToken).getPayloads().getLong(JwtConstant.EXPIRATION);
            long currentTimeStamp = System.currentTimeMillis() / 1000L;
            if (exp > currentTimeStamp) {
                return true;
            }
        }
        return false;
    }


}
