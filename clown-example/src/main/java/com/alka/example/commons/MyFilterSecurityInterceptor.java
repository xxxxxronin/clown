package com.alka.example.commons;

import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Created by len.li on 2016/5/6.
 */
public class MyFilterSecurityInterceptor implements ObjectPostProcessor<FilterSecurityInterceptor> {


    @Override
    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
        object.setPublishAuthorizationSuccess(true);
        return object;
    }
}
