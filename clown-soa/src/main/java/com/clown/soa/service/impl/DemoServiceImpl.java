package com.clown.soa.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.clown.soa.service.DemoService;

/**
 * Created by lenli on 2016/7/15.
 *
 * @Author Libin
 * @Date 2016/7/15
 */
@Service
public class DemoServiceImpl implements DemoService {
    public String sayHello() throws Exception {
        return "Hello Dubbo";
    }
}
