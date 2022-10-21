package com.example.demo.controller;

import com.example.demo.responseResult.ResponseResult;
import com.example.demo.utils.VerifyCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/10/18 16:08
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("/")
@Controller
public class LoginController {


    @RequestMapping("login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView("login");
        try{
            ByteArrayOutputStream img = VerifyCodeUtil.createImg("5d2hk");
            modelAndView.addObject("codeImg",Base64.getEncoder().encodeToString(img.toByteArray()));
        }catch (Exception e){
            e.printStackTrace();
        }
        modelAndView.addObject("sessionId","151111");
        return modelAndView;
    }


    @RequestMapping("loadCode")
    @ResponseBody
    public ResponseResult loadCode(String sessionId){
        try{
            ByteArrayOutputStream img = VerifyCodeUtil.createImg("5d2hk");
            return new ResponseResult(Base64.getEncoder().encodeToString(img.toByteArray()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseResult(500,"ERROR");
    }

    @RequestMapping("doLogin")
    @ResponseBody
    public ResponseResult doLogin(String userName,String password,String sessionId,String code){
         if("admin".equals(userName) && "123456".equals(password) && "5d2hk".equals(code)){
             return new ResponseResult(200,"SUCCESS");
         }else{
             try{
                 ByteArrayOutputStream img = VerifyCodeUtil.createImg("5d2hk");
                 return new ResponseResult(400,"登录失败",Base64.getEncoder().encodeToString(img.toByteArray()));
             }catch (Exception e){
                 e.printStackTrace();
             }
         }
        return new ResponseResult(500,"ERROR");
    }
}
