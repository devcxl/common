package cn.devcxl.common.config.security;

import cn.devcxl.common.config.properties.SecurityJsonWebTokenProperties;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;


/**
 * @author devcxl
 */
@Configuration
public class JsonWebTokenConfig {

    @Resource
    private SecurityJsonWebTokenProperties securityJsonWebTokenProperties;

    public JWTSigner getJsonWebTokenSigner() {
        String secret = securityJsonWebTokenProperties.getSecret();
        return JWTSignerUtil.hs256(secret.getBytes(StandardCharsets.UTF_8));
    }
}
