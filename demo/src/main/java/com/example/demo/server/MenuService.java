package com.example.demo.server;

import com.example.demo.entity.Menu;

import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/6/21 10:52
 * @Version: 1.0.0
 * @Description: 描述
 */
public interface MenuService {
    List<Menu> selectAll();
}
