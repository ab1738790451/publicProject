package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/8/15 14:23
 * @Version: 1.0.0
 * @Description: 描述
 */
public class AbstractController<PK extends Serializable,T> {

    String module;

    public AbstractController(){
        RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class);
        if (rm == null) {
            throw new RuntimeException("Not Found RequestMapping annotation in class " + this.getClass().getName());
        } else {
            String name = rm.name();
            if(name != null && name.trim().length() > 0){
                this.module = name;
            }else{
                String[] modules = rm.value();
                if (modules != null && modules.length > 0 && (modules[0] != null && modules[0].trim().length() != 0)) {
                    this.module = modules[0];
                } else {
                    throw new RuntimeException("module path not defined for RequestMapping annotation in class " + this.getClass().getName());
                }
            }
        }
    }

    protected String getModule() {
        return module;
    }


    @RequestMapping("toedit")
    public ModelAndView toedit(HttpServletRequest request, HttpServletResponse response,String lastLayId,String id){
        ModelAndView modelAndView = new ModelAndView("/testFour");
        modelAndView.addObject("lastLayId",lastLayId);
        modelAndView.addObject("id",id);
        return  modelAndView;
    }
}
