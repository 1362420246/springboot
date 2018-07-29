package com.qbk.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  过滤器
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "blosTest")
@Order(value = 2)//@Order中的value越小，优先级越高。
public class Test2Filter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("过滤器实现2");
        filterChain.doFilter(request, response);
        System.out.println("过滤器实现2，请求后");
    }

    @Override
    public void destroy() {
    }
}
