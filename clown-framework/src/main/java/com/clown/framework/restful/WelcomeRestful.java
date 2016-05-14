package com.clown.framework.restful;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by len.li on 16/3/2016.
 */
@RestController
@RequestMapping(path = "/welcome")
public class WelcomeRestful{

    @RequestMapping(value = "")
    public String index() throws Exception{
        return "Welcome to use the MS Framework!";
    }
}
