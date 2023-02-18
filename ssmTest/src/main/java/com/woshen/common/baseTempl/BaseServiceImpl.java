package com.woshen.common.baseTempl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.beanModel.BeanUtils;
import com.woshen.common.beanModel.PageInfo;
import com.woshen.common.constants.DataStatus;
import com.woshen.common.webcommon.annotation.EnableEncryption;
import com.woshen.common.webcommon.annotation.EncryptionField;
import com.woshen.common.webcommon.utils.EncryptionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
*@company woshen
*@author liuhaibo
*@Date 2023/2/12 22:45
*@version 1.0
*@description
*/
public class BaseServiceImpl<Pk extends Serializable,E extends BaseMapper<T>,T extends BaseEntity<Pk>> extends ServiceImpl<E, T> implements BaseService<Pk,T>{

    @Override
    public QueryWrapper<T> getBaseWrapper(T queryData) {
        try {
            QueryWrapper<T> queryWrapper = new QueryWrapper<>();
            if(queryData == null){
                return queryWrapper;
            }
            Map<String, Object> map = BeanUtils.beanToMap(queryData);
            Class<? extends BaseEntity> aClass = queryData.getClass();
            Field[] fields = aClass.getDeclaredFields();
            EnableEncryption enableEncryption = aClass.getAnnotation(EnableEncryption.class);
            Map<String,Object> allEq = new HashMap<>();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                TableField annotation = field.getAnnotation(TableField.class);
                if(annotation != null && !annotation.exist()){
                    continue;
                }
                String fieldName = field.getName();
                Object o = map.get(fieldName);
                if(o != null && enableEncryption != null &&  o instanceof String){
                    EncryptionField encryptionField = field.getAnnotation(EncryptionField.class);
                    if(encryptionField != null){
                        o = EncryptionUtils.encryption((String) o);
                    }
                }
                allEq.put(StringUtils.humpToUnderline(fieldName),o);
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

    @Override
    public Page<T> selectPage(T queryData) {
        PageInfo pageInfo;
        if(queryData == null){
            pageInfo = new PageInfo();
        }else{
            pageInfo = queryData.getPageInfo();
        }
       return (Page<T>) super.page(new Page<>(pageInfo.getPageIndex(),pageInfo.getPageSize()),getBaseWrapper(queryData));
    }

    @Override
    public void del(Pk... pks) {
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id",pks);
        updateWrapper.set("status", DataStatus.DELETED);
        super.update(updateWrapper);
    }

    @Override
    public Integer dosave(T queryData) {
        if(queryData.getPk() == null){
            super.save(queryData);
        }else{
            super.updateById(queryData);
        }
        return queryData.getPk();
    }
}
