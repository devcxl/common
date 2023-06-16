package cn.devcxl.common.config.security;

import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;


/**
 * @author devcxl
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class JsonWebTokenConfig {

    @Resource
    private SecurityProperties securityProperties;

    public JWTSigner getJsonWebTokenSigner() {
        String secret = securityProperties.getSecret();
        return JWTSignerUtil.hs256(secret.getBytes(StandardCharsets.UTF_8));
    }
}
