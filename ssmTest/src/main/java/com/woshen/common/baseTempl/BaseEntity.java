package com.woshen.common.baseTempl;

import com.woshen.common.beanModel.PageInfo;

import java.io.Serializable;

/**
*@company woshen
*@author liuhaibo
*@Date 2023/2/12 23:06
*@version 1.0
*@description
*/
public abstract class BaseEntity<Pk> implements Serializable {

    private PageInfo pageInfo;

    public PageInfo getPageInfo() {
        return pageInfo == null?new PageInfo():pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public abstract Integer getPk();
}
