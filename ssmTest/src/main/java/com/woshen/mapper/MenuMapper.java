package com.woshen.mapper;

import com.woshen.entity.Menu;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/6/21 10:49
 * @Version: 1.0.0
 * @Description: 描述
 */
@Repository
public interface MenuMapper {
    @Select("select * from menu where status = 'NORMAL'")
    List<Menu> selectAll();
}
