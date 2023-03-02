package com.woshen.controller;

import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.constants.UserType;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.entity.User;
import com.woshen.entity.UserRole;
import com.woshen.service.IUserRoleService;
import com.woshen.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: liuhaibo
 * @Date: 2023/3/2 16:08
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("acl")
@RestController
public class AclAuthController {

    @Resource
    private IUserService userServiceImpl;

    @Resource
    private IUserRoleService userRoleServiceImpl;


    @RequestMapping("/getUserType")
    public ResponseResult getUserType(@RequestParam("userId")Integer userId, @RequestParam("appId")Integer appId){
        User user = userServiceImpl.getById(userId);
        if(user == null || !DataStatus.NORMAL.equals(user.getStatus())){
            return new ResponseResult(500,"用户不存在或已注销");
        }
        UserRole userRole = userRoleServiceImpl.getById(userId);
        if(UserType.ADMIN.equals(userRole.getUserType())){
            String appIds = userRole.getAppIds();
            if(StringUtils.isBlank(appIds)){
                return new ResponseResult(UserType.ORDINARY);
            }
            List<String> apps = Arrays.asList(appIds.split(","));
            if(!apps.contains(appId.toString())){
                return new ResponseResult(UserType.ORDINARY);
            }
        }
        return new ResponseResult(userRole.getUserType());
    }
}
