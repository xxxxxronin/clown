package com.clown.framework.configurations;

/**
 * Created by lenli on 2016/7/7.
 *全局属性配置常量键名
 * @Author Libin
 * @Date 2016/7/7
 */
public class ClownContextPropertiesConstant {
    /**
     * 配置文件存放目录,默认取classpath下的config
     */
    public static final String CLOWN_CONFIG_DIR = "clown.config.dir";

    /**
     * 应用显示名称
     */
    public static final String CLOWN_APPLICATION_NAME = "clown.application.name";
    /**
     * freemarke 模板目录配置key
     */
    public static final String CLOWN_TEMPLATE_DIR = "clown.template.dir";
    /**
     * 静态资源路径目录配置
     */
    public static final String CLOWN_RESOURCES_PATH = "clown.resources.dir";
    /**
     * 是否开启全局跨域
     */
    public static final String CLOWN_ALLOW_CROSSDOMAIN = "clown.allow.crossdomain";


    public final static String CLOWN_CROSS_ALLOWORIGIN = "clown.cross.alloworigin";
    public final static String CLOWN_CROSS_ALLOWHEADERS = "clown.cross.allowheaders";

    public final static String CLOWN_CROSS_ALLOWMETHODS = "clown.cross.allowmethods";
    public final static String CLOWN_CROSS_MAXAGE = "clown.cross.maxage";

    /**
     * 此配置项只有在clown.allow.crossdomain = true 有效，跨域拦截路由
     */
    public static final String CLOWN_CROSSDOMAIN_FILTERMAPPING = "clown.crossdomain.filtermapping";

    public static final String CLOWN_ERROR_PAGE = "clown.error.page";

    /**
     * mybaits 扫描包配置项
     */
    public final static String CLOWN_MYBATIS_BASEPACKAGE = "clown.mybatis.basepackage";

    /**
     * 百度开源 disconf 扫描包配置项
     */
    public final static String CLOWN_DISCONF_SCANPACKAGE = "clown.disconf.scanpackage";
}
