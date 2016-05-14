package com.alka.example.commons;

import org.springframework.security.access.expression.AbstractSecurityExpressionHandler;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * Created by len.li on 2016/5/6.
 */
public class MyAbstractSecurityExpressionHandler extends AbstractSecurityExpressionHandler {
    @Override
    protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, Object invocation) {
        return null;
    }
}
