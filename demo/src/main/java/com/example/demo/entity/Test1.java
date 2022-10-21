package com.example.demo.entity;

import com.example.demo.utils.ObjectUtils;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/5/17 11:25
 * @Version: 1.0.0
 * @Description: 描述
 */
public class Test1 {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ObjectUtils.ObjectToString(this);
    }
}
