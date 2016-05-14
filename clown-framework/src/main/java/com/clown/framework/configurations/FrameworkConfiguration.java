package com.clown.framework.configurations;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by len.li on 25/3/2016.
 */
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Service
@DisconfFile(filename = "config.properties")
public class FrameworkConfiguration {

    private String packageString;

    @DisconfFileItem(name = "ms.disconf.scanpackage",associateField = "packageString")
    public String getPackageString() {
        return packageString;
    }

    public void setPackageString(String packageString) {
        this.packageString = packageString;
    }
}
