package com.woshen.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.baseTempl.AbstractController;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.entity.Role;
import com.woshen.service.IRoleService;
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
@RequestMapping("/role")
public class RoleController extends AbstractController<Integer, Role> {

    @Resource
    private IRoleService roleServiceImpl;

    @Override
    public IRoleService getService() {
        return roleServiceImpl;
    }

    @Override
    public Page<Role> loadList(Role queryData) {
        queryData.setStatus(DataStatus.NORMAL);
        return super.loadList(queryData);
    }
}
