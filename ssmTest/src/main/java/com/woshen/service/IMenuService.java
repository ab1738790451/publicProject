package com.woshen.service;

import com.woshen.common.baseTempl.BaseService;
import com.woshen.entity.Menu;

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

    List<Menu> selectAll(Menu menu);
}
