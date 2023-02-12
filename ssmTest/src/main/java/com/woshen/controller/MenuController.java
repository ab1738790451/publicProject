package com.woshen.controller;

import com.woshen.entity.Menu;
import com.woshen.service.IMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: liuhaibo
 * @Date: 2023/2/12 11:25
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("menu")
@Controller
public class MenuController {

    @Resource
    private IMenuService menuServiceImpl;

    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response, Menu queryData){
        ModelAndView mav = new ModelAndView("/menu/list");
        mav.addObject("pageData",menuServiceImpl.selectPage(queryData));
        mav.addObject("queryData",queryData);
        return mav;
    }
}
