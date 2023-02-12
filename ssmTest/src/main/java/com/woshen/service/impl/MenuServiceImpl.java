package com.woshen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.beanModel.BeanUtils;
import com.woshen.common.beanModel.PageInfo;
import com.woshen.common.beanModel.UserModel;
import com.woshen.entity.Menu;
import com.woshen.mapper.MenuMapper;
import com.woshen.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

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

    public QueryWrapper<Menu> getBaseWrapper(Menu menu) {
        try {
            QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
            if(menu == null){
                return queryWrapper;
            }
            Map<String, Object> map = BeanUtils.beanToMap(menu);
            Field[] fields = menu.getClass().getDeclaredFields();
            Map<String,Object> allEq = new HashMap<>();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                TableField annotation = field.getAnnotation(TableField.class);
                if(annotation != null && !annotation.exist()){
                    continue;
                }
                String fieldName = field.getName();
                allEq.put(humpToUnderline(fieldName),map.get(menu));
            }
            queryWrapper.select(allEq.keySet().stream().toArray(String[]::new));
            if(allEq.entrySet().size() != 0){
                allEq.keySet().stream().forEach(t ->{
                    if(allEq.get(t) != null){
                        queryWrapper.eq(t,allEq.get(t));
                    }
                });
            }
           return queryWrapper;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }


    public String humpToUnderline(String s){
        if(StringUtils.isBlank(s)){
            return null;
        }
        int length = s.length();
        StringBuilder results = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
             if(i != 0 ){
                 if(Character.isUpperCase(c) && s.charAt(i-1) != '_' && !Character.isUpperCase(s.charAt(i-1))){
                     results.append("_");
                     results.append(Character.toLowerCase(c));
                     continue;
                 }
             }
            results.append(c);
        }
        return results.toString();
    }

    @Override
    public Page<Menu> selectPage(Menu menu){
        PageInfo pageInfo = menu.getPageInfo() == null?new PageInfo():menu.getPageInfo();
       return (Page<Menu>) this.getBaseMapper().selectPage(new Page<>(pageInfo.getPageIndex(),pageInfo.getPageSize()),getBaseWrapper(menu));
    }

}
