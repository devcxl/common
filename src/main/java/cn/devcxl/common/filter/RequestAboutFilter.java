package cn.devcxl.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author devcxl
 */
@Slf4j
@Component
public class RequestAboutFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            response.addHeader("x-request-id", requestId);
            response.addHeader("x-request-time-spent", String.valueOf(elapsedTime));
            log.info("请求路径：{}，消耗时间：{}毫秒", request.getRequestURI(), elapsedTime);
        }
    }
}
