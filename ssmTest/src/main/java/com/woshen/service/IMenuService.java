package com.woshen.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.baseTempl.BaseService;
import com.woshen.entity.App;
import com.woshen.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
public interface IMenuService extends BaseService<Integer, Menu> {

    Page<Menu> selectPage(Menu menu);
}
