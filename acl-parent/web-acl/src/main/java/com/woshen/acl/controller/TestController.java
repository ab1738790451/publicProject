package com.woshen.acl.controller;

import com.woshen.common.base.utils.TreeNodeUtil;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.common.webcommon.utils.WebUtils;
import com.woshen.acl.entity.Menu;
import com.woshen.acl.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private IMenuService menuServiceImpl;

    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
        System.err.println("url:"+request.getRequestURL().toString());
        System.err.println("url:"+request.getRequestURI());
        System.err.println("url:"+request.getLocalName());
        System.err.println("os:"+ WebUtils.getOs(request));
        System.err.println("osName:"+WebUtils.getOsName(request));
        System.err.println("deviceType:"+WebUtils.getDeviceType(request));
        System.err.println("borwserType:"+WebUtils.getBrowserType(request));
        System.err.println("borwserName:"+WebUtils.getBrowserName(request));
        System.err.println("ip:"+WebUtils.getIpAddress(request));
        return new ModelAndView("/index");
    }


    @RequestMapping(value = "/three1",method = RequestMethod.GET)
    public String test3(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("id",request.getParameter("id"));
        return "index";
    }

    @RequestMapping(value = "/three",method = RequestMethod.GET)
    public ModelAndView test31(HttpServletRequest request,HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("testThree");
        modelAndView.addObject("menus",menuServiceImpl.selectPage(new Menu()).getRecords());
        modelAndView.addObject("id",request.getParameter("id"));
        return modelAndView;
    }

    @RequestMapping(value = "four",method = RequestMethod.GET)
    public ModelAndView test4(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/test");
       /* File resourceAsFile = Resources.getResourceAsFile("d/ff");
        Resources.getResourceAsStream("555");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(new FileInputStream(resourceAsFile));*/
        return mav;
    }


    @RequestMapping(value = "six",method = RequestMethod.GET)
    public String six(HttpServletRequest request,HttpServletResponse response){
        return "testTwo";
    }

    @RequestMapping(value = "loadMenu",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult loadMenu(HttpServletRequest request, HttpServletResponse response){
        return new ResponseResult(new TreeNodeUtil(menuServiceImpl.selectPage(new Menu()).getRecords()).getTreeDatas());
    }

    @PostMapping("dosave")
    @ResponseBody
    public ResponseResult dosave(HttpServletRequest request,HttpServletResponse response){
        return new ResponseResult(200,"成功");
    }


    @RequestMapping("toEdit")
    @ResponseBody
    public ModelAndView toEdit(HttpServletRequest request,HttpServletResponse response,Integer lastLayId,Integer id){
        ModelAndView modelAndView = new ModelAndView("/testFour");
        modelAndView.addObject("lastLayId",lastLayId);
        modelAndView.addObject("id",id);
        return  modelAndView;
    }

    @RequestMapping("test")
    @ResponseBody
    public ResponseResult test(HttpServletResponse response) throws IOException {
        return new ResponseResult(200);
    }
}
