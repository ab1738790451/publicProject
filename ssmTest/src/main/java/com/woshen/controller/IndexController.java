package com.woshen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: liuhaibo
 * @Date: 2023/2/10 9:53
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("/")
@Controller
public class IndexController {

    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/index");
    }
}
