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
     * mybaits 扫描包配置项
     */
    private final static String MS_MYBATIS_BASEPACKAGE = "ms.mybatis.basepackage";
    /**
     * 百度开源 disconf 扫描包配置项
     */
    private final static String MS_DISCONF_SCANPACKAGE = "ms.disconf.scanpackage";
    private final static String MS_APPLICATION_NAME = "ms.application.name";
    private final static String MS_CROSS_DOMAIN = "ms.allow.crossdomain";
    private final static String MS_CROSS_DOMAIN_URL = "ms.crossdomain.filtermapping";

    private final static String MS_CROSS_ALLOWORIGIN = "clown.cross.alloworigin";
    private final static String MS_CROSS_ALLOWHEADERS = "clown.cross.allowheaders";

    private final static String MS_CROSS_ALLOWMETHODS = "clown.cross.allowmethods";
    private final static String MS_CROSS_MAXAGE = "clown.cross.maxage";


    public static String getMsMybatisBasepackage(){
        return properties.getProperty(MS_MYBATIS_BASEPACKAGE);
    }

    public static String getMsDisconfScanpackage(){
        return properties.getProperty(MS_MYBATIS_BASEPACKAGE);
    }


    public static String getMsApplicationName(){
        return  properties.getProperty(MS_APPLICATION_NAME);
    }


    public static boolean getMsCrossDomain(){
        return properties.getProperty(MS_CROSS_DOMAIN).toLowerCase().equals("true");
    }

    public static String getMsCrossDomainUrl(){
        return properties.getProperty(MS_CROSS_DOMAIN_URL).toLowerCase();
    }

    public static String getMsCrossAlloworigin(){
        return properties.getProperty(MS_CROSS_ALLOWORIGIN);
    }

    public static String getMsCrossAllowheaders(){
        return properties.getProperty(MS_CROSS_ALLOWHEADERS);
    }

    public static String getMsCrossAllowmethods(){
        return properties.getProperty(MS_CROSS_ALLOWMETHODS);
    }
    public static String getMsCrossMaxage(){
        return properties.getProperty(MS_CROSS_MAXAGE);
    }





}
