package com.woshen.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.baseTempl.AbstractController;
import com.woshen.common.baseTempl.BaseService;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.entity.Menu;
import com.woshen.service.IMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author: liuhaibo
 * @Date: 2023/2/12 11:25
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("menu")
@Controller
public class MenuController extends AbstractController<Integer,Menu> {

    @Resource
    private IMenuService menuServiceImpl;

    @Override
    public BaseService getService() {
        return menuServiceImpl;
    }

    @Override
    public Page<Menu> loadList(Menu queryData) {
        queryData.setStatus(DataStatus.NORMAL);
        return super.loadList(queryData);
    }
}
