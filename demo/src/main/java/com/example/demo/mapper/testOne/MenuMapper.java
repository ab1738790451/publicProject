package com.example.demo.mapper.testOne;

import com.example.demo.entity.Menu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/6/21 10:49
 * @Version: 1.0.0
 * @Description: 描述
 */
@Mapper
public interface MenuMapper {
    @Select("select * from Menu where status = 'NORMAL'")
    List<Menu> selectAll();

    @Insert("insert into menu(title,url,parentId,status,create_time,update_time) values(#{title},#{url},#{parentId},#{status},#{createTime},#{updateTime})")
    void insert(Menu menu);
}
