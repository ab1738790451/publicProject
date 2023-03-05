package com.woshen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.baseTempl.AbstractController;
import com.woshen.common.constants.UserType;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.common.webcommon.model.DefaultUserModel;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.common.webcommon.utils.ThreadWebLocalUtil;
import com.woshen.entity.App;
import com.woshen.entity.Role;
import com.woshen.entity.User;
import com.woshen.entity.UserRole;
import com.woshen.service.IAppService;
import com.woshen.service.IRoleService;
import com.woshen.service.IUserRoleService;
import com.woshen.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController<Integer, User> {

    @Resource
    private IUserService userServiceImpl;

    @Resource
    private IAppService appServiceImpl;

    @Resource
    private IUserRoleService userRoleServiceImpl;

    @Resource
    private IRoleService roleServiceImpl;

    @Override
    public IUserService getService() {
        return userServiceImpl;
    }

    @Override
    public Page<User> loadList(User queryData) {
        queryData.setStatus(DataStatus.NORMAL);
        DefaultUserModel user = ThreadWebLocalUtil.getUser();
        if(user !=null){
            queryData.addQueryParam("currId",user.getUserId());
        }
        return super.loadList(queryData);
    }

    @Override
    public Map<String, ?> afterEdit(HttpServletRequest request, HttpServletResponse response, User data) {
        Map<String,Object> resultMap = new HashMap<>();
        App app = new App();
        app.setStatus(DataStatus.NORMAL);
        List<App> apps = appServiceImpl.selectList(app);
        resultMap.put("apps",apps);
        if(data != null){
            UserRole userRole = userRoleServiceImpl.getById(data.getId());
            data.setUserType(userRole.getUserType());
            if(StringUtils.isNotBlank(userRole.getAppIds())){
                List<String> list = Arrays.asList(userRole.getAppIds().split(","));
                data.setAppIds(list);
                apps.stream().forEach( t -> {
                    if(list.contains(t.getId().toString())){
                        t.setChecked(true);
                    }
                });
            }
        }
        DefaultUserModel userModel = ThreadWebLocalUtil.getUser();
        if(userModel != null){
            User user = userServiceImpl.getById(userModel.getUserId());
            UserRole userRole = userRoleServiceImpl.getById(user.getId());
            resultMap.put("currUserId",user.getId());
            resultMap.put("currUserType",userRole.getUserType());
        }
        return resultMap;
    }

     @RequestMapping("managerRole")
     public ModelAndView managerRole(String lastLayId,Integer userId){
         ModelAndView modelAndView = new ModelAndView(super.getModule()+"/managerRole");
         modelAndView.addObject("lastLayId",lastLayId);
         App app = new App();
         app.setStatus(DataStatus.NORMAL);
         QueryWrapper<App> baseWrapper = appServiceImpl.getBaseWrapper(app);
         User user = userServiceImpl.getById(userId);
         UserRole userRole = userRoleServiceImpl.getById(userId);
         if(UserType.ADMIN.equals(userRole.getUserType())){
             String appIds = userRole.getAppIds();
             if(StringUtils.isNotBlank(appIds)){
                 String[] split = appIds.split(",");
                 baseWrapper.notIn("id",split);
                /* user.setAppIds(Arrays.asList(split));*/
             }
         }
         List<App> apps = appServiceImpl.list(baseWrapper);
         List<Integer> ids = apps.stream().map(App::getId).collect(Collectors.toList());
         Role role = new Role();
         role.setStatus(DataStatus.NORMAL);
         QueryWrapper<Role> roleQueryWrapper = roleServiceImpl.getBaseWrapper(role);
         roleQueryWrapper.in("app_id",ids);
         List<Role> list = roleServiceImpl.list(roleQueryWrapper);
         if(!CollectionUtils.isEmpty(list)){
             String roleIds = userRole.getRoleIds();
             if(StringUtils.isNotBlank(roleIds)){
                 List<String> roles = Arrays.asList(roleIds.split(","));
                 list.stream().forEach( t ->{
                     if(roles.contains(t.getId().toString())){
                         t.setChecked(true);
                     }
                 });
                 modelAndView.addObject("roleIds",roles);
             }
             Map<Integer, List<Role>> collect = list.stream().collect(Collectors.groupingBy(Role::getAppId));
             apps.stream().forEach( t -> t.setRoles(collect.get(t.getId())));
         }
         modelAndView.addObject("apps",apps);
         modelAndView.addObject("user",user);
         return modelAndView;
     }

     @RequestMapping("saveRoles")
     @ResponseBody
     public ResponseResult  saveRoles(@RequestBody User user){
         Integer userId = user.getId();
         if(userId == null || userId < 1){
            return new ResponseResult(400,"参数错误");
        }
         List<String> roleIds = user.getRoleIds();
         UserRole userRole = new UserRole();
        userRole.setId(userId);
         String join;
         if(CollectionUtils.isEmpty(roleIds)){
            join = "";
        }else{
             join = String.join(",", roleIds);
         }
        userRole.setRoleIds(join);
        userRoleServiceImpl.dosave(userRole);
        return new ResponseResult(200,"SUCCESS");
     }
}
