package com.clown.framework.core;

import com.baidu.disconf.client.DisconfMgrBean;
import com.clown.framework.configurations.PropertiesConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.Ordered;

/**
 * Created by len.li on 24/3/2016.
 * 这个类主要是为了降低Disconf初始化顺序并通过config.properties文件来配置scanPackage属性
 */
public class DefaultDisconfMgrBean extends DisconfMgrBean {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        String scanPackage = PropertiesConfiguration.getMsDisconfScanpackage();
        setScanPackage(String.format("%s,%s","com.clown.framework.configurations",scanPackage));
        super.postProcessBeanDefinitionRegistry(registry);

    }

}
