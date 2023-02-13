package com.woshen.common.baseTempl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;

/**
 * @author liuhaibo
 * @version 1.0
 * @company woshen
 * @Date 2023/2/12 22:43
 * @description
 */
public interface BaseService<Pk extends Serializable,T extends BaseEntity<Pk>> extends IService<T> {

    QueryWrapper<T> getBaseWrapper(T queryData);

    Page<T> selectPage(T queryData);

    void del(Pk... pks);

    Integer dosave(T queryData);
}
