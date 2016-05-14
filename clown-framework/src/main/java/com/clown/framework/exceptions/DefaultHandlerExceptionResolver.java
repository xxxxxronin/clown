package com.clown.framework.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.clown.framework.DefaultAjaxResult;
import com.clown.framework.DefaultRequestCode;
import com.clown.framework.utils.ExceptionUtil;
import com.clown.framework.utils.JsonUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by len.li  on 9/15/2015.
 * 统一异常处理
 */
public class DefaultHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

    private String errorPage = "redirect:/error";

    public DefaultHandlerExceptionResolver() {
        // 将异常处理类优先级提到最高
        setOrder(1);
    }

    /**
     * 判断是否AJAX请求
     * @param request
     * @return
     */
    protected boolean isAjaxRequest(HttpServletRequest request){
        boolean ajax = "XMLHttpRequest".equals( request.getHeader("X-Requested-With") );
        int ajaxFlag = null == request.getParameter("ajax") ?  0: Integer.valueOf(request.getParameter("ajax"));
        boolean isAjax = ajax || ajaxFlag==1;
        return isAjax;
    }

    /**
     * 处理显示异常消息
     * @param httpServletResponse
     * @param message
     * @param contentType
     * @return
     */
    protected ModelAndView responseHandlerComplte(HttpServletResponse httpServletResponse,String message,String contentType){

        try {
            httpServletResponse.setStatus(400);
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setHeader("Content-type",contentType);
            httpServletResponse.getWriter().print(message);
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return new ModelAndView();
    }

    /**
     * 处理显示HTML 异常消息处理
     * @param httpServletResponse
     * @param message
     * @return
     */
    protected ModelAndView responseHandlerComplteHtml(HttpServletResponse httpServletResponse,String message){
        return responseHandlerComplte(httpServletResponse,message,"text/html;charset=UTF-8");
    }

    /**
     * 处理显示AJAX 异常消息处理
     * @param httpServletResponse
     * @param message
     * @return
     */
    protected ModelAndView responseHandlerComplteJson(HttpServletResponse httpServletResponse,String message){
        return responseHandlerComplte(httpServletResponse,message,"application/json;charset=UTF-8");
    }


    /**
     * 统一处理异常入口
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        if(isAjaxRequest(request)){
            ObjectMapper objectMapper = new ObjectMapper();
            DefaultAjaxResult defaultAjaxResult = new DefaultAjaxResult();
            defaultAjaxResult.setInfo(ex.getMessage());
            defaultAjaxResult.setCode(DefaultRequestCode.REQUEST_FAIL);

            defaultAjaxResult.setInfo(ExceptionUtil.getMessage(ex));
            String str = JsonUtil.toJson(defaultAjaxResult);
            return responseHandlerComplteJson(response,str);
        }
        else{

            return responseHandlerComplteHtml(response,ExceptionUtil.getMessage(ex));
        }

    }

    public void setErrorPage(String errorPage) {
        this.errorPage = "redirect:"+errorPage;
    }

    public String getErrorPage() {
        return errorPage;
    }
}
