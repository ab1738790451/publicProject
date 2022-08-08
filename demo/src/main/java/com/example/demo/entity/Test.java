package com.example.demo.entity;

import com.example.demo.annotation.annotations.FieldSetVal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/4/26 14:40
 * @Version: 1.0.0
 * @Description: 描述
 */
@Component
public class Test {
    @FieldSetVal(val = "力谋")
    private String name;
    @Value("1")
    private int id;

    @FieldSetVal(val = "李四")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public static String getNames(String name){
        StringBuilder s = new StringBuilder();
        s.append(name);
        s.append("输出：");
        s.append(name);
        return s.toString();
    }
}
