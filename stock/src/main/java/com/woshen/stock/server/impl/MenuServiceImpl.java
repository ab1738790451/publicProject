package com.woshen.stock.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woshen.stock.entity.Menu;
import com.woshen.stock.mapper.MenuMapper;
import com.woshen.stock.server.MenuService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/6/21 10:52
 * @Version: 1.0.0
 * @Description: 描述
 */
@Service
public class MenuServiceImpl  extends ServiceImpl<MenuMapper, Menu> implements MenuService{

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> selectAll() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status","NORMAL");
        return menuMapper.selectList(queryWrapper);
    }
}
