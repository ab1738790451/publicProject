package com.example.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/6/8 15:26
 * @Version: 1.0.0
 * @Description: 描述
 */
@Data
public class DataPage {
    public DataPage(){
        this.currentPage = 1;
        this.pageSize = 30;
        this.startIndex = 0;
    }
    public DataPage(long currentPage, long size){
        this.currentPage = currentPage;
        this.pageSize = size;
        this.startIndex = (this.currentPage-1) * this.pageSize <0?0:(this.currentPage-1) * this.pageSize;
    }
    private List<MemberUserVo> records;
    private long total;
    private long pageSize;
    private long startIndex;
    private long currentPage;
}
