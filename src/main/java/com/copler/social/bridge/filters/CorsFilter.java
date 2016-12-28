package com.copler.social.bridge.filters;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author Ivan_Zhuravel
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Origin, Access-Control-Expose-Headers");
        response.setHeader("Access-Control-Max-Age", "3600");
        if (!Objects.equals(request.getMethod(), "OPTIONS")) {
            chain.doFilter(req, res);
        }
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}
