package com.clown.framework.resolvers;

import com.clown.framework.annotations.JsonParameter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by len.li on 2016/4/25.
 * 控制上进行多参json请求绑定,目前不支持数据对象绑定
 */
public class RequestJsonBody implements HandlerMethodArgumentResolver {

    final static int BUFFER_SIZE = 4096;

    private static final String JSONBODYATTRIBUTE = "JSON_REQUEST_BODY";

    private ObjectMapper om = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonParameter.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {



        Object result=null;
        JsonParameter jsonParameter = parameter.getParameterAnnotation(JsonParameter.class);
        String name =jsonParameter.value();
        if(StringUtils.isEmpty(name)){
            name = parameter.getParameterName();
        }
        JsonNode rootNode = om.readTree(getRequestBody(webRequest));
        JsonNode node = rootNode.path(name);
        if(!(node.isNull() || StringUtils.isEmpty(node.toString()))){
            result = om.readValue(node.toString(), parameter.getParameterType());
        }
        return result;
    }

    private String InputStreamToString(InputStream inputStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while((count = inputStream.read(data,0,BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(),"UTF-8");
    }


    private String getRequestBody(NativeWebRequest webRequest) throws Exception{

        String inputStream = (String) webRequest.getAttribute(JSONBODYATTRIBUTE, NativeWebRequest.SCOPE_REQUEST);

        if (inputStream==null){
            HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
            inputStream =InputStreamToString(servletRequest.getInputStream());
            webRequest.setAttribute(JSONBODYATTRIBUTE,inputStream, NativeWebRequest.SCOPE_REQUEST);
        }
        if(inputStream.equals("")){
            throw new HttpMessageNotWritableException("Not Found Request Body!");
        }

        return inputStream;
    }
}
