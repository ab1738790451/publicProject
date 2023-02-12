package com.woshen.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public interface IMenuService extends IService<Menu> {

    List<Menu> selectAll();

    Page<Menu> selectPage(Menu menu);
}
