package com.woshen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.baseTempl.AbstractController;
import com.woshen.common.constants.UserType;
import com.woshen.common.webcommon.exception.BaseRuntimeException;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.common.webcommon.model.DefaultUserModel;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.common.webcommon.utils.ThreadWebLocalUtil;
import com.woshen.entity.App;
import com.woshen.entity.Role;
import com.woshen.entity.User;
import com.woshen.service.IAppService;
import com.woshen.service.IRoleService;
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
            if(StringUtils.isNotBlank(data.getAppIds())){
                List<String> list = Arrays.asList(data.getAppIds().split(","));
                data.setApps(list);
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
            resultMap.put("currUserId",user.getId());
            resultMap.put("currUserType",user.getUserType());
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
         if(UserType.ADMIN.equals(user.getUserType())){
             String appIds = user.getAppIds();
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
             String roleIds = user.getRoleIds();
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
         if(StringUtils.isBlank(user.getRoleIds())){
            user.setRoleIds("");
        }
        userServiceImpl.dosave(user);
        return new ResponseResult(200,"SUCCESS");
     }

    @Override
    public Integer dosave(User queryData) {
        checkAuth(queryData);
        return super.dosave(queryData);
    }

    private void checkAuth(User user){
         UserType userType = user.getUserType();
         if(userType != null && userType.equals(UserType.SUPER_ADMIN)){
             DefaultUserModel user1 = ThreadWebLocalUtil.getUser();
             if(user1 != null && !"1".equals(user1.getUserId())){
                 throw new BaseRuntimeException("不允许设置用户为超级管理员");
             }
         }
     }

    @Override
    public ResponseResult del(Integer... pks) {
        if(pks != null && pks.length > 0){
            DefaultUserModel user1 = ThreadWebLocalUtil.getUser();
            if(user1 != null && !"1".equals(user1.getUserId())){
                User user = new User();
                user.setUserType(UserType.SUPER_ADMIN);
                QueryWrapper<User> baseWrapper = userServiceImpl.getBaseWrapper(user);
                baseWrapper.in("id",pks);
                List<User> list = userServiceImpl.list(baseWrapper);
                if(!CollectionUtils.isEmpty(list)){
                    throw new BaseRuntimeException("不允许删除超级管理员");
                }
            }
        }
        return super.del(pks);
    }
}
