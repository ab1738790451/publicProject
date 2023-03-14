package com.woshen.acl.mapper;

import com.woshen.acl.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("select * from menu where status ='NORMAL' and is_menu = 'Y'")
    public List<Menu> selectAll(Menu menu);
}
