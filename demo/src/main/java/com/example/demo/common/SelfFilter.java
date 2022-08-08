package com.example.demo.common;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/4/21 9:14
 * @Version: 1.0.0
 * @Description: 自定义过滤器
 */
@Component
public class SelfFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
         SelfHttpServletRequestWapper  requestWapper = null;
          if(servletRequest instanceof HttpServletRequest){
             requestWapper = new SelfHttpServletRequestWapper((HttpServletRequest) servletRequest);
          }
          if(requestWapper == null){
              filterChain.doFilter(servletRequest,servletResponse);
          }else{
              filterChain.doFilter(requestWapper,servletResponse);
          }
    }

    @Override
    public void destroy() {

    }

}
