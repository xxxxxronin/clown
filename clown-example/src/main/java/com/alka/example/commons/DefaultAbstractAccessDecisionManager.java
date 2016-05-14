package com.alka.example.commons;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

/**
 * Created by len.li on 2016/5/6.
 * 自定义授权验证策略，通过用户信息与请求URL从DB中配置是否有权限访问
 */
public class DefaultAbstractAccessDecisionManager implements AccessDecisionManager {

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        FilterInvocation filterInvocation = (FilterInvocation) object;
        System.out.println(filterInvocation.getFullRequestUrl());
        System.out.println(filterInvocation.getRequestUrl());

        if(authentication.isAuthenticated()){
            throw new AccessDeniedException("您没有访问权限,请联系管理员.");
        }
        else{
            throw new AccessDeniedException("您没有访问权限,请联系管理员.");
        }

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        logger.debug(clazz);
        return true;
    }


}
