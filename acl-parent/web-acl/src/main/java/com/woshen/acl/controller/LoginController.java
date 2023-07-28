package com.woshen.acl.controller;

import com.woshen.common.base.utils.ByteUtil;
import com.woshen.common.base.utils.RandomUtils;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.base.utils.VerifyCodeUtil;
import com.woshen.common.springweb.utils.LoginUtils;
import com.woshen.common.webcommon.model.DefaultUserModel;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.common.webcommon.utils.DefaultLoginUtils;
import com.woshen.common.webcommon.utils.LocalCahceUtil;
import com.woshen.common.webcommon.utils.ThreadWebLocalUtil;
import com.woshen.acl.entity.User;
import com.woshen.acl.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
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

    @Resource
    private IUserService userServiceImpl;

    @RequestMapping("login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView("login");
        try{
            String code = RandomUtils.getRandomByLowerCaseAndNum((byte) 5);
            ByteArrayOutputStream img = VerifyCodeUtil.createImg(code);
            modelAndView.addObject("codeImg",Base64.getEncoder().encodeToString(img.toByteArray()));
            String sessionId = "session-" + RandomUtils.getRandomByCase((byte) 6) + LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            LocalCahceUtil.set(sessionId,code,2*60);
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
    public ResponseResult doLogin(HttpServletRequest request, HttpServletResponse response, String loginId, String password, String sessionId, String code){

        if(StringUtils.isBlank(sessionId) || StringUtils.isBlank(loginId) || StringUtils.isBlank(password)){
            return new ResponseResult(500,"ERROR");
        }
        String s = LocalCahceUtil.get(sessionId);
        if(s == null){
            return new ResponseResult(500,"ERROR");
        }
        String errorMsg;
        if(!s.equals(code.toLowerCase())){
            errorMsg = "验证码错误！";
        }else{
            User user = new User();
            user.setLoginId(loginId);
            user.setPassword(ByteUtil.byteToHexadecimal(DigestUtils.md5Digest(password.getBytes())));
            User us = userServiceImpl.getOne(userServiceImpl.getBaseWrapper(user));
            if(us == null){
                errorMsg = "账号或密码错误！";
            }else{
                DefaultUserModel defaultUserModel = new DefaultUserModel();
                defaultUserModel.setLoginId(us.getLoginId());
                defaultUserModel.setUserId(us.getId().toString());
                defaultUserModel.setUserName(us.getName());
                defaultUserModel.setHeadImg(us.getHeadImg());
                defaultUserModel.setMobilePhone(us.getMobilePhone());
                defaultUserModel.setNickeName(us.getNickeName());
                defaultUserModel.setEmail(us.getEmail());
                DefaultLoginUtils.setTokenToCookie(response, LoginUtils.doLogin(request,defaultUserModel));
                return new ResponseResult(200,"SUCCESS");
            }
        }
         try{
             String newCode = RandomUtils.getRandomByLowerCaseAndNum((byte) 5);
             ByteArrayOutputStream img = VerifyCodeUtil.createImg(newCode);
             LocalCahceUtil.setAndExtT(sessionId,newCode,2*60,true);
             return new ResponseResult(400,errorMsg,Base64.getEncoder().encodeToString(img.toByteArray()));
         }catch (Exception e){
             e.printStackTrace();
         }
        return new ResponseResult(500,"ERROR");
    }

    @PostMapping("loginOut")
    @ResponseBody
    public ResponseResult loginOut(HttpServletRequest request, HttpServletResponse response){
        DefaultUserModel user = ThreadWebLocalUtil.getUser();
        String userId = user.getUserId();
        LoginUtils.tickOut(userId,null);
        return new ResponseResult(null);
    }

    @RequestMapping("modifyPassword")
    public ModelAndView modifyPassword(){
        return new ModelAndView("modifyPassword");
    }

    @RequestMapping("doModifyPassword")
    @ResponseBody
    public ResponseResult doModifyPassword(@RequestBody()Map<String,Object> params){
        String oldPassword = (String) params.get("oldPassword");
        String password = (String) params.get("password");
        if(StringUtils.isBlank(oldPassword) || StringUtils.isBlank(password)){
            return new ResponseResult(500,"参数错误");
        }
        DefaultUserModel userModel = ThreadWebLocalUtil.getUser();
        String userId = userModel.getUserId();
        User user = userServiceImpl.getById(Integer.valueOf(userId));
        String oldSecret = ByteUtil.byteToHexadecimal(DigestUtils.md5Digest(oldPassword.getBytes()));
        if(!user.getPassword().equals(oldSecret)){
            return new ResponseResult(500,"您输入的旧密码有误，请重新输入");
        }
        user.setPassword(ByteUtil.byteToHexadecimal(DigestUtils.md5Digest(password.getBytes())));
        userServiceImpl.dosave(user);
        return new ResponseResult(null);
    }


    @RequestMapping("getPassword")
    @ResponseBody
    public ResponseResult getPassword(){
        return new ResponseResult(ByteUtil.byteToHexadecimal(DigestUtils.md5Digest("123456".getBytes())));
    }
}
