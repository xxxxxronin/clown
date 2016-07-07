package com.clown.framework.configurations;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.Properties;

/**
 * Created by lenli on 2016/7/6.
 *此类主要功能是根据系统的属性配置 clown.config.dir 指定配置文件所有目录,默认配置目录在classpath根目录,可应用不同的部署环境加载不同的配配置文件
 * @Author Libin
 * @Date 2016/7/6
 */
public class ClownPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {




    public ClownPropertyPlaceholderConfigurer() {
        Properties properties = new Properties();
        properties.setProperty(ClownContextPropertiesConstant.CLOWN_TEMPLATE_DIR,"/template/");
        properties.setProperty(ClownContextPropertiesConstant.CLOWN_RESOURCES_PATH,"/static/");
        properties.setProperty(ClownContextPropertiesConstant.CLOWN_ALLOW_CROSSDOMAIN,"false");
        properties.setProperty(ClownContextPropertiesConstant.CLOWN_CROSSDOMAIN_FILTERMAPPING,"/restful/*");
        properties.setProperty(ClownContextPropertiesConstant.CLOWN_APPLICATION_NAME,"welcome use clow framework!");
        properties.setProperty(ClownContextPropertiesConstant.CLOWN_ERROR_PAGE,"/404");
        setProperties(properties);
    }

    @Override
    public void setLocation(Resource location) {
        super.setLocation(findResource(location));
    }

    @Override
    public void setLocations(Resource... locations) {
        int count = locations.length;
        for (int i =0;i<count;i++) {
            locations[i] = findResource(locations[i]);
        }
        super.setLocations(locations);
    }

    private Resource findResource(Resource resource){
        String dir = System.getProperty(ClownContextPropertiesConstant.CLOWN_CONFIG_DIR);
        if(dir == null){
            return resource;
        }
        File dirF = new File(dir);
        if(dirF.exists() && dirF.isDirectory()){
            logger.info(String.format("加载配置文件位置:%s",dir));
            String configPath = String.format("%s/%s",dirF.getAbsolutePath(),resource.getFilename());
            File configFile = new File(configPath);
            if(configFile.exists() && configFile.isFile()){
                return  new FileSystemResource(configFile);
            }
        }
        return resource;
    }



}
