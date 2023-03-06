package com.woshen.common.baseTempl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.woshen.common.beanModel.PageInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
*@company woshen
*@author liuhaibo
*@Date 2023/2/12 23:06
*@version 1.0
*@description
*/
public abstract class BaseEntity<Pk> implements Serializable {
    @TableField(exist = false)
    private PageInfo pageInfo;

    @TableField(exist = false)
    private Map<String,Object> queryParam;

    public PageInfo getPageInfo() {
        return pageInfo = pageInfo == null?new PageInfo():pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public Map<String, Object> getQueryParam() {
        return queryParam = queryParam==null?new HashMap<>():queryParam;
    }

    public Object getQueryParam(String key) {
        return getQueryParam().get(key);
    }

    public void setQueryParam(Map<String, Object> queryParam) {
        this.queryParam = queryParam;
    }

    public void addQueryParam(String key,Object value){
        getQueryParam().put(key,value);
    }

    public abstract Integer getPk();
}
