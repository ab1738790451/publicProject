package com.example.demo.server;

import com.example.demo.entity.Table;

import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/4/26 15:18
 * @Version: 1.0.0
 * @Description: 描述
 */
public interface TestService {

    public void test();

    void add(Table table);

    List<Table> queryAll();

    void get();
}
