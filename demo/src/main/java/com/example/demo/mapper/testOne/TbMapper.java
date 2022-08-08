package com.example.demo.mapper.testOne;

import com.example.demo.entity.Tb;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/7/26 11:03
 * @Version: 1.0.0
 * @Description: 描述
 */
public interface TbMapper {

    @Select("SELECT * FROM tb")
    List<Tb> selectAll();
}
