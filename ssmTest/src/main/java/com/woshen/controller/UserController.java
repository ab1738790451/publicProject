package com.woshen.controller;


import com.woshen.common.baseTempl.AbstractController;
import com.woshen.entity.Role;
import com.woshen.entity.User;
import com.woshen.service.IRoleService;
import com.woshen.service.IUserService;
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
@RequestMapping("/user")
public class UserController extends AbstractController<Integer, User> {

    @Resource
    private IUserService userServiceImpl;

    @Override
    public IUserService getService() {
        return userServiceImpl;
    }
}
