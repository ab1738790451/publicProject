package com.woshen.mapper;

import com.woshen.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
public interface MenuMapper extends BaseMapper<Menu> {
    @Select("select * from menu where status = 'NORMAL'")
    List<Menu> selectAll();
}
