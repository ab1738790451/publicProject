package com.woshen.controller;

import com.woshen.common.base.utils.TreeNodeUtil;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.entity.Menu;
import com.woshen.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: liuhaibo
 * @Date: 2023/2/10 9:53
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("/")
@Controller
public class IndexController {

    @Autowired
    private IMenuService menuServiceImpl;

    @RequestMapping("index")
    public ModelAndView index(){
        return new ModelAndView("/index");
    }


    @RequestMapping(value = "loadMenu",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult loadMenu(HttpServletRequest request, HttpServletResponse response){
        String header = request.getHeader("X-Requested-With");
        Menu menu = new Menu();
        menu.setIsMenu("Y");
        return new ResponseResult(new TreeNodeUtil(menuServiceImpl.selectAll(menu)).getTreeDatas());
    }
}
