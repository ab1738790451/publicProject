package com.example.demo.controller;

import com.example.demo.responseResult.ResponseResult;
import com.example.demo.utils.LocalCahceUtil;
import com.example.demo.utils.VerifyCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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
            String sessionId = "session-" + LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            LocalCahceUtil.set(sessionId,"5d2hk",100*60);
            modelAndView.addObject("sessionId",sessionId);
        }catch (Exception e){
            e.printStackTrace();
        }

        return modelAndView;
    }


    @RequestMapping("loadCode")
    @ResponseBody
    public ResponseResult loadCode(String sessionId){
        try{
            LocalCahceUtil.deleted(sessionId);
            String sessionId2 = "session-" + LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            ByteArrayOutputStream img = VerifyCodeUtil.createImg("5d2hk");
            LocalCahceUtil.set(sessionId,"5d2hk",100*60);
            Map<String,Object> data= new HashMap<>();
            data.put("code",Base64.getEncoder().encodeToString(img.toByteArray()));
            data.put("sessionId",sessionId2);
            return new ResponseResult(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseResult(500,"ERROR");
    }

    @RequestMapping("doLogin")
    @ResponseBody
    public ResponseResult doLogin(String userName,String password,String sessionId,String code){
        String s = LocalCahceUtil.get(sessionId);
        if(s != null){
            if("admin".equals(userName) && "123456".equals(password) && s.equals(code)){
                return new ResponseResult(200,"SUCCESS");
            }
        }
         try{
             ByteArrayOutputStream img = VerifyCodeUtil.createImg("5d2hk");
             LocalCahceUtil.setAndExtT(sessionId,"5d2hk",100*60,true);
             return new ResponseResult(400,"登录失败",Base64.getEncoder().encodeToString(img.toByteArray()));
         }catch (Exception e){
             e.printStackTrace();
         }
        return new ResponseResult(500,"ERROR");
    }
}
