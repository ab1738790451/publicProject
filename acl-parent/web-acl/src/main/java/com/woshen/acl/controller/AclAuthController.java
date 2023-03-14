package com.woshen.acl.controller;

import com.woshen.acl.common.constants.AclAuthKeyNs;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.acl.constants.UserType;
import com.woshen.common.redis.utils.RedisUtil;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.acl.entity.*;
import com.woshen.acl.service.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private IAppService appServiceImpl;

    @Resource
    private IRoleService roleServiceImpl;

    @Resource
    private IMenuService menuServiceImpl;


    @RequestMapping("/getUser")
    public ResponseResult getUserType(@RequestParam("userId")Integer userId, @RequestParam("appId")Integer appId){
        User user = userServiceImpl.getById(userId);
        if(user == null || !DataStatus.NORMAL.equals(user.getStatus())){
            return new ResponseResult(500,"用户不存在或已注销");
        }
        if(UserType.ADMIN.equals(user.getUserType())){
            String appIds = user.getAppIds();
            if(StringUtils.isBlank(appIds)){
                user.setUserType(UserType.ORDINARY);
            }else{
                List<String> apps = Arrays.asList(appIds.split(","));
                if(!apps.contains(appId.toString())){
                    user.setUserType(UserType.ORDINARY);
                }
            }

        }
        return new ResponseResult(user);
    }

    @PostMapping("getUrlAccessRoles")
    public ResponseResult getUrlAccessRoles( @RequestParam("appId")Integer appId){
        App app = appServiceImpl.getById(appId);
        if(app == null || !DataStatus.NORMAL.equals(app.getStatus())){
            return new ResponseResult(500,"应用不存在或已删除");
        }
        Menu menu = new Menu();
        menu.setStatus(DataStatus.NORMAL);
        menu.setAppId(appId);
        List<Menu> menus = menuServiceImpl.selectList(menu);
        if(CollectionUtils.isEmpty(menus)){
            return new ResponseResult(500,"错误的uri");
        }
        Role role = new Role();
        role.setAppId(appId);
        role.setStatus(DataStatus.NORMAL);
        List<Role> roles = roleServiceImpl.selectList(role);
        Map<String,String> menuRoleMapping = new HashMap<>();
        //建立菜单于角色的映射关系
        for (Role item:roles
             ) {
            String menuIds = item.getMenuIds();
            if(StringUtils.isBlank(menuIds)){
                continue;
            }
            String[] split = menuIds.split(",");
            for (String menuId:split
                 ) {
                String s = menuRoleMapping.get(menuId);
                if(StringUtils.isBlank(s)){
                    s = item.getId().toString();
                }else {
                    s += ","+item.getId().toString();
                }
                menuRoleMapping.put(menuId,s);
            }
        }
        Map<String,String> uriRoleMapping = new HashMap<>();
        menus.stream().forEach( t ->{
            String roleIds = menuRoleMapping.get(t.getId().toString());
            if(StringUtils.isNotBlank(roleIds)){
                uriRoleMapping.put(t.getUrl(),roleIds);
            }else{
                uriRoleMapping.put(t.getUrl(),"0");
            }
        });
       return new ResponseResult(uriRoleMapping);
    }

    @RequestMapping("delMenuRoleMappingCache")
    public ResponseResult delMenuRoleMappingCache(@RequestParam("appId") Integer appId){
       RedisUtil.delKey(AclAuthKeyNs.ACL_URL_ACCESS_ROLES, appId);
       return new ResponseResult(200,"刷新成功");
    }
}
