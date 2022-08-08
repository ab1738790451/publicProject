package com.example.demo.mapper.testTwo;


import com.example.demo.entity.Tbt;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/7/26 11:04
 * @Version: 1.0.0
 * @Description: 描述
 */
public interface TbtMapper {

    @Select("SELECT * FROM tbt")
    List<Tbt> selectAll();
}
