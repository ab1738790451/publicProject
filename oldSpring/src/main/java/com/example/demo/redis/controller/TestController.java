package com.example.demo.redis.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2023/2/8 10:06
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("/")
@RestController
public class TestController {


    @RequestMapping("test")
    public String test(@RequestBody Map map){
        return "SUCCESS";
    }
}
