package com.woshen.controller;


import com.woshen.common.baseTempl.AbstractController;
import com.woshen.entity.Role;
import com.woshen.entity.UserRole;
import com.woshen.service.IRoleService;
import com.woshen.service.IUserRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
@Controller
@RequestMapping("/userRole")
public class UserRoleController extends AbstractController<Integer, UserRole> {

    @Resource
    private IUserRoleService userRoleServiceImpl;

    @Override
    public IUserRoleService getService() {
        return userRoleServiceImpl;
    }
}
