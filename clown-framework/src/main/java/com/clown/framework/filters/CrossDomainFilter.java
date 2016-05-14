package com.clown.framework.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by len.li on 2016/4/21.
 * 支持跨域过滤器
 * 配置文件键值对如下
 * clown.cross.alloworigin=* clown.cross.allowheaders=Origin    clown.cross.allowmethods=GET,POST,OPTIONS  clown.cross.maxage=3628800
 */
public class CrossDomainFilter implements Filter {

    private String crossAllowOrigin = "*";
    private String crossAllowHeaders = "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With";
    private String crossAllowMethods = "GET,POST,OPTIONS";
    private String crossMaxAge = "3628800";

    public CrossDomainFilter() {
    }

    public CrossDomainFilter(String crossAllowOrigin, String crossAllowHeaders, String crossAllowMethods, String crossMaxAge) {
        this.crossAllowOrigin = crossAllowOrigin;
        this.crossAllowHeaders = crossAllowHeaders;
        this.crossAllowMethods = crossAllowMethods;
        this.crossMaxAge = crossMaxAge;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", this.crossAllowOrigin);
        response.setHeader("Access-Control-Allow-Headers", this.crossAllowHeaders);
        response.setHeader("Access-Control-Allow-Methods", this.crossAllowMethods);
        response.setHeader("Access-Control-Max-Age",this.crossMaxAge);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if(httpServletRequest.getMethod().toLowerCase().equals("options")){
            String requestMethod = httpServletRequest.getHeader("Access-Control-Request-Method").toLowerCase();
            if(requestMethod.equals("get") || requestMethod.equals("post")){
                response.setStatus(204);
            }
            else{
                response.setStatus(400);
            }
        }
        else{
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {
    }
}
