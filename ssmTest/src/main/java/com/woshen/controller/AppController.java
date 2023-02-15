package com.woshen.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.baseTempl.AbstractController;
import com.woshen.common.baseTempl.BaseService;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.entity.App;
import com.woshen.service.IAppService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

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
@RequestMapping("/app")
public class AppController extends AbstractController<Integer, App> {

    @Resource
    private IAppService appServiceImpl;

    @Override
    public IAppService getService() {
        return appServiceImpl;
    }

    @Override
    public Page<App> loadList(App queryData) {
        queryData.setStatus(DataStatus.NORMAL);
        return super.loadList(queryData);
    }
}
