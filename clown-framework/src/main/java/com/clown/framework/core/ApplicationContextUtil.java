package com.clown.framework.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Locale;

/**
 * @Package com.clown.framework.core
 * @ClassName ApplicationContextUtil
 * @Description
 * @author len
 * @date 2015年4月28日
 * 
 */

public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void registerBean(String beanname,BeanDefinition beanDefinition){
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) getApplicationContext().getAutowireCapableBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition(beanname,beanDefinition);
    }

    public static Object getBean(Class<?> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name)
            throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    public static Class<?> getType(String name)
            throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }

    public static String[] getAliases(String name)
            throws NoSuchBeanDefinitionException {
        return applicationContext.getAliases(name);
    }

    public static String getMessage(String code) {
        return getMessage(code, null, null, Locale.CHINA);
    }

    public static String getMessage(String code, Object[] args) {
        return getMessage(code, args, null, Locale.CHINA);
    }

    public static String getMessage(String code, Object[] args,
            String defaultMessage) {
        return getMessage(code, args, defaultMessage, Locale.CHINA);
    }

    public static String getMessage(String code, Object[] args,
            String defaultMessage, Locale locale) {
        return getApplicationContext().getMessage(code, args, defaultMessage,
                locale);
    }


}