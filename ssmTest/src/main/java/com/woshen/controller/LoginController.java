package com.woshen.controller;

import com.woshen.common.base.utils.RandomUtils;
import com.woshen.common.base.utils.VerifyCodeUtil;
import com.woshen.common.springweb.utils.LoginUtils;
import com.woshen.common.webcommon.model.DefaultUserModel;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.common.webcommon.utils.LocalCahceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liuhaibo
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
            String code = RandomUtils.getRandomByLowerCaseAndNum((byte) 5);
            ByteArrayOutputStream img = VerifyCodeUtil.createImg(code);
            modelAndView.addObject("codeImg",Base64.getEncoder().encodeToString(img.toByteArray()));
            String sessionId = "session-" + RandomUtils.getRandomByCase((byte) 6) + LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            LocalCahceUtil.set(sessionId,code,5*60);
            modelAndView.addObject("sessionId",sessionId);
        }catch (Exception e){
            e.printStackTrace();
        }

        return modelAndView;
    }


    @PostMapping("loadCode")
    @ResponseBody
    public ResponseResult loadCode(String sessionId){
        try{
            /*LocalCahceUtil.deleted(sessionId);
            String sessionId2 = "session-"+ RandomUtils.getRandomByCase((byte) 6) + LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();*/
            String code = RandomUtils.getRandomByLowerCaseAndNum((byte) 5);
            ByteArrayOutputStream img = VerifyCodeUtil.createImg(code);
            LocalCahceUtil.setAndExtT(sessionId, code,2*60,true);
            Map<String,Object> data= new HashMap<>();
            data.put("code",Base64.getEncoder().encodeToString(img.toByteArray()));
            data.put("sessionId",sessionId);
            return new ResponseResult(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseResult(500,"ERROR");
    }

    @PostMapping("doLogin")
    @ResponseBody
    public ResponseResult doLogin(HttpServletRequest request, HttpServletResponse response, String userName, String password, String sessionId, String code){
        String s = LocalCahceUtil.get(sessionId);
        if(s != null){
            if("admin".equals(userName) && "123456".equals(password) && s.equals(code)){
                DefaultUserModel defaultUserModel = new DefaultUserModel();
                defaultUserModel.setLoginId("admin");
                defaultUserModel.setUserId("admin");
                LoginUtils.setTokenToCookie(response,LoginUtils.doLogin(request,defaultUserModel));
                return new ResponseResult(200,"SUCCESS");
            }
        }
         try{
             String newCode = RandomUtils.getRandomByLowerCaseAndNum((byte) 5);
             ByteArrayOutputStream img = VerifyCodeUtil.createImg(newCode);
             LocalCahceUtil.setAndExtT(sessionId,newCode,2*60,true);
             return new ResponseResult(400,"登录失败",Base64.getEncoder().encodeToString(img.toByteArray()));
         }catch (Exception e){
             e.printStackTrace();
         }
        return new ResponseResult(500,"ERROR");
    }
}
