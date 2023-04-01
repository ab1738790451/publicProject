package com.woshen.acl.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woshen.acl.common.constants.AclAuthKeyNs;
import com.woshen.acl.constants.UserType;
import com.woshen.acl.entity.App;
import com.woshen.acl.entity.Menu;
import com.woshen.acl.entity.Role;
import com.woshen.acl.entity.User;
import com.woshen.acl.service.IAppService;
import com.woshen.acl.service.IMenuService;
import com.woshen.acl.service.IRoleService;
import com.woshen.acl.service.IUserService;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.base.utils.TreeNodeUtil;
import com.woshen.common.redis.utils.RedisUtil;
import com.woshen.common.webcommon.model.Bool;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.common.webcommon.model.ResponseResult;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
            String url = t.getUrl();
            if(StringUtils.isNotBlank(url)){
                String[] urls = url.split(",");
                for (String u:urls
                     ) {
                    if(StringUtils.isNotBlank(roleIds)){
                        uriRoleMapping.put(u,roleIds);
                    }else{
                        uriRoleMapping.put(u,"0");
                    }
                }
            }

        });
       return new ResponseResult(uriRoleMapping);
    }

    @RequestMapping("delMenuRoleMappingCache")
    public ResponseResult delMenuRoleMappingCache(@RequestParam("appId") Integer appId){
       RedisUtil.delKey(AclAuthKeyNs.ACL_URL_ACCESS_ROLES, appId);
        List<User> list = userServiceImpl.lambdaQuery().eq(User::getStatus, DataStatus.NORMAL).apply("(user_type = 'SUPER_ADMIN' or find_in_set('" + appId + "',app_ids) )").list();
        if(!CollectionUtils.isEmpty(list)){
            list.stream().forEach(t -> {
                RedisUtil.delKey(AclAuthKeyNs.ACL_USER_MENU, appId + "-" + t.getId());
            });
        }
        return new ResponseResult(200,"刷新成功");
    }


    @RequestMapping(value = "loadMenu",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult loadMenu(@RequestParam("appId")Integer appId,@RequestParam("userId")Integer userId){

        List<Menu> treeDatas = null;
        String key = appId + "-" + userId;
        String s = RedisUtil.stringExecutor().get(AclAuthKeyNs.ACL_USER_MENU, key);
        if(StringUtils.isNotBlank(s)){
            treeDatas = JSONArray.parseArray(s,Menu.class);
            return new ResponseResult(treeDatas);
        }
        User user = userServiceImpl.getById(Integer.valueOf(userId));
        UserType userType = user.getUserType();
        if(UserType.ADMIN.equals(userType)){
            String appIds = user.getAppIds();
            if(StringUtils.isNotBlank(appIds)){
                String[] split = appIds.split(",");
                if(Arrays.asList(split).contains(appId.toString())){
                    userType = UserType.SUPER_ADMIN;
                }
            }
        }

        if(UserType.SUPER_ADMIN.equals(userType)){
            Menu menu = new Menu();
            menu.setIsMenu(Bool.Y);
            menu.setAppId(appId);
            menu.setStatus(DataStatus.NORMAL);
            treeDatas = new TreeNodeUtil(menuServiceImpl.selectList(menu)).getTreeDatas();
        }else{
            String roleIds = user.getRoleIds();
            if(StringUtils.isNotBlank(roleIds)){
                String[] split = roleIds.split(",");
                Role role = new Role();
                role.setStatus(DataStatus.NORMAL);
                role.setAppId(appId);
                QueryWrapper<Role> baseWrapper = roleServiceImpl.getBaseWrapper(role);
                baseWrapper.in("id",split);
                List<Role> list = roleServiceImpl.list(baseWrapper);
                Set<Integer> mIds = new HashSet<>();
                list.stream().forEach(t->{
                    String menuIdstr = t.getMenuIds();
                    if(StringUtils.isNotBlank(menuIdstr)){
                        String[] menuIds = menuIdstr.split(",");
                        List<Integer> collect = Arrays.asList(menuIds).stream().map(item -> Integer.valueOf(item)).collect(Collectors.toList());
                        mIds.addAll(collect);
                    }
                });
                if(!CollectionUtils.isEmpty(mIds)){
                    Menu menu = new Menu();
                    menu.setIsMenu(Bool.Y);
                    menu.setAppId(appId);
                    menu.setStatus(DataStatus.NORMAL);
                    QueryWrapper<Menu> menuQueryWrapper = menuServiceImpl.getBaseWrapper(menu);
                    menuQueryWrapper.in("id",mIds);
                    menuQueryWrapper.orderByAsc("id");
                    menuQueryWrapper.orderByDesc("priority");
                    List<Menu> list1 = menuServiceImpl.list(menuQueryWrapper);
                    treeDatas = new TreeNodeUtil(list1).getTreeDatas();
                }
            }
        }
        if(!CollectionUtils.isEmpty(treeDatas)){
            List<Menu> collect = treeDatas.stream().filter(t -> t.getParent() == -1).collect(Collectors.toList());
            RedisUtil.stringExecutor().set(AclAuthKeyNs.ACL_USER_MENU,key,JSONObject.toJSONString(collect));
        }
        return new ResponseResult(treeDatas);
    }

}
