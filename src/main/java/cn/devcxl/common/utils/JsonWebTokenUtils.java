package cn.devcxl.common.utils;

import cn.devcxl.common.config.security.JsonWebTokenConfig;
import cn.devcxl.common.config.security.SecurityJsonWebTokenProperties;
import cn.devcxl.common.constant.JwtConstant;
import cn.hutool.jwt.JWTUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

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

    @Resource
    private SecurityJsonWebTokenProperties securityJsonWebTokenProperties;

    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        Map<String, Object> header = new HashMap<>();
//        header.put(JwtConstant.IIS,);

        header.put(JwtConstant.SUB, username);

        header.put(JwtConstant.EXPIRATION, System.currentTimeMillis() / 1000 + JwtConstant.DEFAULT_EXPIRATION);
        return null;
    }

    public String getUserNameFromToken(String authToken) {
        return JWTUtil.parseToken(authToken).getPayloads().get(JwtConstant.SUB, String.class);
    }

    /**
     * 验证token是否有效&是否过期&自动刷新有效期
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
        if (JWTUtil.verify(token, jsonWebTokenConfig.getJsonWebTokenSigner())) {
            Long exp = JWTUtil.parseToken(token).getPayloads().get(JwtConstant.EXPIRATION, Long.class);
            long currentTimeStamp = System.currentTimeMillis() / 1000L;
            if (exp > currentTimeStamp) {
                // todo: 过期时间离当前时间还剩一小时时刷新token

                if (exp - currentTimeStamp <= 3600) {
                    return
                }


                return true;
            }
        }
        // todo: 刷新token
        return token;
    }

}
