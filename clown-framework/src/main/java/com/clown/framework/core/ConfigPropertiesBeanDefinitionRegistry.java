package com.clown.framework.core;

import com.clown.framework.configurations.ClownPropertyPlaceholderConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;


/**
 * Created by len.li on 24/3/2016.
 * 最优先加载config.properties 初始化propertyConfigurer 占位符替换配置
 */
public class ConfigPropertiesBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor,PriorityOrdered, ApplicationContextAware {

    public final static String DEFUALT_PROPERTIES="config.properties";

    private boolean useCutomRequestMappingHandlerMapping= false;

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.add("location","classpath:"+DEFUALT_PROPERTIES);
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(ClownPropertyPlaceholderConfigurer.class);
        beanDefinition.setLazyInit(false);
        beanDefinition.setAbstract(false);
        beanDefinition.setAutowireCandidate(true);
        beanDefinition.setScope("singleton");
        beanDefinition.setPropertyValues(mutablePropertyValues);
        registry.registerBeanDefinition("propertyConfigurer",beanDefinition);
//        org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation()
        // 注册自定义request mapping 处理器
        if(useCutomRequestMappingHandlerMapping){
            beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(CustomeRequestMappingHandlerMapping.class);
            beanDefinition.setLazyInit(false);
            beanDefinition.setAbstract(false);
            beanDefinition.setAutowireCandidate(true);
            registry.registerBeanDefinition("org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping",beanDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    public boolean isUseCutomRequestMappingHandlerMapping() {
        return useCutomRequestMappingHandlerMapping;
    }

    public void setUseCutomRequestMappingHandlerMapping(boolean useCutomRequestMappingHandlerMapping) {
        this.useCutomRequestMappingHandlerMapping = useCutomRequestMappingHandlerMapping;
    }
}
