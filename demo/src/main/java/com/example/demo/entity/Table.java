package com.example.demo.entity;

import com.example.demo.common.ObjectUtils;

import java.io.Serializable;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/5/17 11:23
 * @Version: 1.0.0
 * @Description: 描述
 */
public class Table implements Serializable {

    private Long id;
    private String name;
    private Integer age;
    private String sex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return ObjectUtils.ObjectToString(this);
    }
}
