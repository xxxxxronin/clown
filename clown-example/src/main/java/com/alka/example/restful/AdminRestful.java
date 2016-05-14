package com.alka.example.restful;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenli on 16/4/24.
 */
@RestController
@RequestMapping("/admin")
public class AdminRestful {

    @RequestMapping(value = {"","/","/index"})
    public String index() throws Exception{
        return "Hello Security!";
    }
}
