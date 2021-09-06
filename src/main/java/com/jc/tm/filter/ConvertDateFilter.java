package com.jc.tm.filter;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class ConvertDateFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("inter filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(servletRequest.getAttribute("name"));
        String data = servletRequest.getParameter("name");
        System.out.println(data);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        System.out.println("destroy filter");
    }
}
