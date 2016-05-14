package com.alka.example.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by len.li on 23/3/2016.
 */
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Service
@DisconfFile(filename = "common.properties")
public class CommonConf {

    private String prot;

    @DisconfFileItem(name = "cs.prot",associateField = "prot")
    public String getProt() {
        return prot;
    }

    public void setProt(String prot) {
        this.prot = prot;
    }
}
