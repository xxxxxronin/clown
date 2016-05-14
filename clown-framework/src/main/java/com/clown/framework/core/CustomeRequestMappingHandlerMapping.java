package com.clown.framework.core;

import com.clown.framework.RestFulControllerInter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * Created by len.li on 24/3/2016.
 * 这个类主要是改变requestMapping url访问地址,只有继承了RestFulControllerInter这个口接的mapping会自动加上前缀/restful
 */
public class CustomeRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private boolean isRestful = false;
    private final static String RESTFUL_PREFIX = "/restful";

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        isRestful = RestFulControllerInter.class.isAssignableFrom(handlerType);
        RequestMapping requestMapping = handlerType.getAnnotation(RequestMapping.class);
        RequestMappingInfo root;
        RequestMappingInfo requestMappingInfo= super.getMappingForMethod(method, handlerType);

        if(isRestful && requestMapping!=null && requestMappingInfo!=null){
            PatternsRequestCondition patterns = new PatternsRequestCondition(RESTFUL_PREFIX);
            RequestMethodsRequestCondition methods = requestMappingInfo.getMethodsCondition();
            ParamsRequestCondition params =requestMappingInfo.getParamsCondition();
            HeadersRequestCondition headers = requestMappingInfo.getHeadersCondition();
            ConsumesRequestCondition consumes = requestMappingInfo.getConsumesCondition();
            ProducesRequestCondition produces =requestMappingInfo.getProducesCondition();
            RequestConditionHolder custom = (RequestConditionHolder) requestMappingInfo.getCustomCondition();
            if(custom!=null){
                root = new RequestMappingInfo( requestMappingInfo.getName(),patterns,methods, params, headers, consumes, produces, custom.getCondition());
            }
            else {
                root = new RequestMappingInfo( requestMappingInfo.getName(),patterns,methods, params, headers, consumes, produces, null);
            }
           root =  root.combine(requestMappingInfo);
        }
        else {
            root = requestMappingInfo;
        }
        return  root;
    }






}
