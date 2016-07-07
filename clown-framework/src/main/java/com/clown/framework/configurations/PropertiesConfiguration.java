package com.clown.framework.configurations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

/**
 * Created by len.li on 2016/4/13.
 * 提供在启动容器时需要获取相关配置信息操作类
 */
public abstract class PropertiesConfiguration {

    protected final static Log logger = LogFactory.getLog(PropertiesConfiguration.class);
    

    private  final static Properties properties;



    static {
        properties = new Properties();
        try {
            properties.load(PropertiesConfiguration.class.getResourceAsStream("/config.properties"));
        } catch (Exception e) {
            logger.info("not find config.properties");
        }
    }


    /**
     * 获得百度配置扫描包名
     * @return
     */
    public static String getDisconfScanpackage(){
        String value = properties.getProperty(ClownContextPropertiesConstant.CLOWN_DISCONF_SCANPACKAGE,"com.clown.code.config");
        return value;
    }

    /**
     * 是否开启跨域
     * @return
     */
    public static boolean getCrossDomain(){
        String value = properties.getProperty(ClownContextPropertiesConstant.CLOWN_ALLOW_CROSSDOMAIN,"false");
        return value.toLowerCase().equals("true");
    }

    public static String findPropertieValue(String key,String defaultValue){
        return  properties.getProperty(key,defaultValue);
    }


}
