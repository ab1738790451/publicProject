package com.woshen.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woshen.stock.entity.Menu;
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
public interface MenuMapper extends BaseMapper<Menu> {
   /* @Select("select * from Menu where status = 'NORMAL'")
    List<Menu> selectAll();*/

}
