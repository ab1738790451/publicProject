package com.woshen.acl.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.base.utils.TreeNodeUtil;
import com.woshen.common.webcommon.db.action.AbstractController;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.acl.entity.Menu;
import com.woshen.acl.entity.Role;
import com.woshen.acl.service.IMenuService;
import com.woshen.acl.service.IRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
@Controller
@RequestMapping("role")
public class RoleController extends AbstractController<Integer, Role> {

    @Resource
    private IRoleService roleServiceImpl;
    @Resource
    private IMenuService menuServiceImpl;

    @Override
    public IRoleService getService() {
        return roleServiceImpl;
    }

    @Override
    public ModelAndView toedit(HttpServletRequest request, HttpServletResponse response, String lastLayId, Integer pk) {
        ModelAndView mav = super.toedit(request, response, lastLayId, pk);
        String appId = request.getParameter("appId");
        if(StringUtils.isNotBlank(appId)){
            Object data = mav.getModel().get("data");
            if(data ==null){
                Role role = new Role();
                role.setAppId(Integer.valueOf(appId));
                mav.addObject("data",role);
            }
        }
        return mav;
    }

    @Override
    public Page<Role> loadList(Role queryData) {
        queryData.setStatus(DataStatus.NORMAL);
        return super.loadList(queryData);
    }


    @RequestMapping("loadRoleMenu")
    @ResponseBody
    public ResponseResult loadRoleMenu(Integer pk,Integer appId){
        Menu menu = new Menu();
        String menuIds = null;
        if(pk != null && pk > 0){
            Role role = roleServiceImpl.getById(pk);
            menuIds = role.getMenuIds();
        }
        menu.setAppId(appId);
        menu.setStatus(DataStatus.NORMAL);
        List<Menu> list = menuServiceImpl.list(menuServiceImpl.getBaseWrapper(menu));
        if(StringUtils.isNotBlank(menuIds)){
            String[] split = menuIds.split(",");
            List<String>  ids = Arrays.asList(split);
            list.stream().forEach( t -> {
                if(ids.contains(t.getId().toString())){
                    t.setChecked(true);
                }
            });
        }
        return new ResponseResult(new TreeNodeUtil<>(list));
    }
}
