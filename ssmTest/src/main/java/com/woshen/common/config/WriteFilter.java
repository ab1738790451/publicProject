package com.woshen.common.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: liuhaibo
 * @Date: 2023/2/12 10:32
 * @Version: 1.0.0
 * @Description: 描述
 */
public class WriteFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletResponse instanceof HttpServletResponse){
            servletResponse.setContentType("text/html;charset=utf-8");
            servletResponse.getWriter().write("<script>alert('哈哈')</script>");
            servletResponse.flushBuffer();
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
