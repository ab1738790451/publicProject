package com.woshen.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.baseTempl.AbstractController;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.entity.App;
import com.woshen.entity.Role;
import com.woshen.entity.User;
import com.woshen.entity.UserRole;
import com.woshen.service.IAppService;
import com.woshen.service.IRoleService;
import com.woshen.service.IUserRoleService;
import com.woshen.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public IUserService getService() {
        return userServiceImpl;
    }

    @Override
    public Page<User> loadList(User queryData) {
        queryData.setStatus(DataStatus.NORMAL);
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
        return resultMap;
    }
}
