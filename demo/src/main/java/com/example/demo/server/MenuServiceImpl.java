package com.example.demo.server;

import com.example.demo.entity.Menu;
import com.example.demo.mapper.testOne.MenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/6/21 10:52
 * @Version: 1.0.0
 * @Description: 描述
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> selectAll() {
        return menuMapper.selectAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(){
        List<Menu> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Menu menu = new Menu();
            menu.setCreateTime(LocalDateTime.now());
            menu.setParentId(i);
            menu.setStatus("NORMAL");
            menu.setTitle("ss");
            menu.setUpdateTime(LocalDateTime.now());
            menu.setUrl("http://baidu.com");
            list.add(menu);
        }
        list.stream().forEach( t ->{
            menuMapper.insert(t);
            if(t.getParentId() == 3){
                throw new RuntimeException("测试");
            }
        });

    }
}
