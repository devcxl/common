package cn.devcxl.common.component;

import cn.devcxl.common.config.properties.SecurityJsonWebTokenProperties;
import cn.devcxl.common.utils.JsonWebTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录授权过滤器
 * @author devcxl
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private SecurityJsonWebTokenProperties securityJsonWebTokenProperties;

    @Resource
    private JsonWebTokenUtils jsonWebTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String headerName = securityJsonWebTokenProperties.getHeaderName();
        String tokenPrefix = securityJsonWebTokenProperties.getTokenPrefix();
        String authHeader = request.getHeader(headerName);

        if (authHeader != null && authHeader.startsWith(tokenPrefix)) {
            String authToken = authHeader.substring(tokenPrefix.length());
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jsonWebTokenUtils.validateToken(authToken)) {
                    String username = jsonWebTokenUtils.getUserNameFromToken(authToken);
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("authenticated user:{}", username);
                }
            }
        }
        chain.doFilter(request, response);
    }
}