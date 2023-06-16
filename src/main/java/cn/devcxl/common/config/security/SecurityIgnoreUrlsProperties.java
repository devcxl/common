package cn.devcxl.common.config.security;

import cn.devcxl.common.constant.JwtConstant;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author devcxl
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.security.ignore.urls")
public class SecurityIgnoreUrlsProperties {
    private List<String> urls = new ArrayList<>();


}
