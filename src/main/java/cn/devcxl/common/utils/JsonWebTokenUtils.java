package cn.devcxl.common.utils;

import cn.devcxl.common.config.security.JsonWebTokenConfig;
import cn.devcxl.common.constant.JwtConstant;
import cn.hutool.jwt.JWTUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author devcxl
 */
@Component
public class JsonWebTokenUtils {

    @Resource
    private JsonWebTokenConfig jsonWebTokenConfig;

    @Resource
    private JsonWebTokenUtils jsonWebTokenUtils;

    @Resource
    private UserDetailsService userDetailsService;

    public String getUserNameFromToken(String authToken) {
        return JWTUtil.parseToken(authToken).getPayloads().get(JwtConstant.USERNAME, String.class);
    }

    /**
     * 验证token是否有效&是否过期
     *
     * @param authToken
     * @return
     */
    public boolean validateToken(@NotBlank String authToken) {

        if (StringUtils.hasLength(authToken)) {
            String jwtUsername = jsonWebTokenUtils.getUserNameFromToken(authToken);

            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUsername);

            if (JWTUtil.verify(authToken, jsonWebTokenConfig.getJsonWebTokenSigner())) {

                Long exp = JWTUtil.parseToken(authToken).getPayloads().get(JwtConstant.EXPIRATION, Long.class);
                long currentTimeStamp = System.currentTimeMillis() / 1000L;
                if (exp > currentTimeStamp) {
                    return true;
                }

            }
        }

        return false;
    }


    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {

        // todo: 刷新token
        return null;
    }

}
