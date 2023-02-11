package com.woshen.service.impl;

import com.woshen.entity.Menu;
import com.woshen.mapper.MenuMapper;
import com.woshen.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> selectAll(){
        return this.getBaseMapper().selectAll();
    }
}
