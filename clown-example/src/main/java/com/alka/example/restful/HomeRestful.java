package com.alka.example.restful;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lenli on 16/4/24.
 */
@Controller
@RequestMapping(value = "/home")
public class HomeRestful {

    @RequestMapping(value = {"","/","/index"})
    public String index() throws Exception{
        return "Hello Security!";
    }
}
