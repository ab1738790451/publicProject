package com.woshen.acl.service;

import com.woshen.common.webcommon.db.service.BaseService;
import com.woshen.acl.entity.Menu;
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
