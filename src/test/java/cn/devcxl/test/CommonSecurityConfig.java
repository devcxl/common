package cn.devcxl.test;

import cn.devcxl.common.annotation.switchs.EnableSecurityConfig;
import cn.devcxl.common.component.*;
import cn.devcxl.common.config.security.SecurityIgnoreUrlsProperties;
import cn.devcxl.common.constant.EnvConstant;
import cn.devcxl.common.exception.controller.CustomErrorController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Slf4j
@Configuration
@EnableSecurityConfig
@Import(CustomErrorController.class)
public class CommonSecurityConfig {
    @Resource
    private SecurityIgnoreUrlsProperties securityIgnoreUrlsProperties;
    @Resource
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired(required = false)
    private DynamicSecurityService dynamicSecurityService;
    @Autowired(required = false)
    private DynamicSecurityFilter dynamicSecurityFilter;

    @Value(EnvConstant.ENV_VALUE)
    private String env;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();

        log.info("securityIgnoreUrlsProperties:{}", securityIgnoreUrlsProperties.getUrls());
        //不需要保护的资源路径允许访问
        for (String url : securityIgnoreUrlsProperties.getUrls()) {
            registry.antMatchers(url).permitAll();
        }
        //允许跨域请求的OPTIONS请求
        registry.antMatchers(HttpMethod.OPTIONS).permitAll().antMatchers(HttpMethod.GET, // Swagger的资源路径需要允许访问
                "/", "/swagger-ui.html", "/swagger-ui/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/swagger-resources/**", "/v3/api-docs/**").permitAll();


        // 开发环境配置h2控制台访问
        if (env.equals(EnvConstant.DEV)) {
            httpSecurity.headers().frameOptions().disable();
            registry.antMatchers("/h2-console/**").permitAll();
        }

        // 任何请求需要身份认证
        registry.and().authorizeRequests().anyRequest().authenticated()
                // 关闭跨站请求防护及不使用session
                .and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 自定义权限拒绝处理类
                .and().exceptionHandling().accessDeniedHandler(restfulAccessDeniedHandler).authenticationEntryPoint(restAuthenticationEntryPoint)
                // 自定义权限拦截器JWT过滤器
                .and().addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //有动态权限配置时添加动态权限校验过滤器
        if (dynamicSecurityService != null) {
            registry.and().addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
        }
        return httpSecurity.build();
    }

}
