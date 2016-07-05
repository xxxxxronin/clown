package com.clown.code.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lenli on 2016/7/4.
 *
 * @Author Libin
 * @Date 2016/7/4
 */

@Controller
@RequestMapping(value = "")
public class CodeController {

    @RequestMapping(value ={"","/index","/"})
    public String index(Model model) throws Exception{
        return "home/index";
    }
}
