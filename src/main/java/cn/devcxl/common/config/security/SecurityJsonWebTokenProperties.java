package cn.devcxl.common.config.security;

import cn.devcxl.common.constant.JwtConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author devcxl
 */
@Data
@ConfigurationProperties(prefix = "spring.security.jwt")
public class SecurityJsonWebTokenProperties {

    /**
     * Token加密密钥
     */
    private String secret;

    /**
     * 请求头名称
     */
    private String headerName = JwtConstant.TOKEN_HEADER_NAME;

    /**
     * Token前缀
     */
    private String tokenPrefix = JwtConstant.TOKEN_PREFIX;

    /**
     * Token有效时长 单位:秒
     */
    private Integer expiration = JwtConstant.DEFAULT_EXPIRATION;

}
