package com.woshen.stock.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woshen.stock.entity.Menu;

import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/6/21 10:52
 * @Version: 1.0.0
 * @Description: 描述
 */
public interface MenuService  extends IService<Menu> {
    List<Menu> selectAll();
}
