package com.woshen.common.beanModel;

/**
 * @Author: liuhaibo
 * @Date: 2023/2/12 14:00
 * @Version: 1.0.0
 * @Description: 描述
 */
public class PageInfo {

    private Integer pageIndex;

    private Integer pageSize;
    public PageInfo(){}

    public PageInfo(Integer pageIndex,Integer pageSize){
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex == null?1:pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize == null?9999:pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
