package cn.devcxl.common.utils;

import cn.devcxl.common.config.security.JsonWebTokenConfig;
import cn.devcxl.common.config.security.SecurityJsonWebTokenProperties;
import cn.devcxl.common.constant.JwtConstant;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class JsonWebTokenUtils {

    @Resource
    private JsonWebTokenConfig jsonWebTokenConfig;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private SecurityJsonWebTokenProperties securityJsonWebTokenProperties;

    /**
     * 根据用户信息生成token
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        Map<String, Object> header = new HashMap<>();
        header.put(JwtConstant.IIS, securityJsonWebTokenProperties.getIis());
        header.put(JwtConstant.SUB, username);
        header.put(JwtConstant.EXPIRATION, System.currentTimeMillis() / 1000 + securityJsonWebTokenProperties.getExpiration());
        return JWTUtil.createToken(header, jsonWebTokenConfig.getJsonWebTokenSigner());
    }


    /**
     * 从Token中获取用户名
     *
     * @param authToken
     * @return
     */
    public String getUserNameFromToken(String authToken) {
        return JWTUtil.parseToken(authToken).getPayloads().get(JwtConstant.SUB, String.class);
    }

    /**
     * 验证token是否有效&是否过期
     *
     * @param authToken
     * @return
     */
    public boolean validateToken(@NotBlank String authToken) {
        if (StringUtils.hasLength(authToken)) {
            try {
                if (JWTUtil.verify(authToken, jsonWebTokenConfig.getJsonWebTokenSigner())) {
                    Long exp = JWTUtil.parseToken(authToken).getPayloads().get(JwtConstant.EXPIRATION, Long.class);
                    long currentTimeStamp = System.currentTimeMillis() / 1000L;
                    if (exp > currentTimeStamp) {
                        return true;
                    }
                }
            } catch (Exception exception) {
                log.error("JWT验证失败:{}", exception.getMessage());
                return false;
            }
        }
        return false;
    }

    /**
     * 刷新token有效期
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        if (JWTUtil.verify(token, jsonWebTokenConfig.getJsonWebTokenSigner())) {
            Long exp = JWTUtil.parseToken(token).getPayloads().get(JwtConstant.EXPIRATION, Long.class);
            long currentTimeStamp = System.currentTimeMillis() / 1000L;
            if (exp > currentTimeStamp) {
                if (exp - currentTimeStamp <= 3600) {
                    String username = getUserNameFromToken(token);
                    return generateToken(userDetailsService.loadUserByUsername(username));
                }
            }
        }
        return token;
    }

}
