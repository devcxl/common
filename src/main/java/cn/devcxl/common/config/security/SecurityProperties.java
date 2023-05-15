package cn.devcxl.common.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author devcxl
 */
@Data
@ConfigurationProperties(prefix = "spring.security.jwt")
public class SecurityProperties {

    /**
     * Token加密密钥
     */
    private String secret;

    /**
     * 请求头名称
     */
    private String headerName = "Authorization";

    /**
     * Token前缀
     */
    private String tokenPrefix = "Bearer ";

    /**
     * Token有效时长 单位:秒
     */
    private Integer expiration = 86400;

}
