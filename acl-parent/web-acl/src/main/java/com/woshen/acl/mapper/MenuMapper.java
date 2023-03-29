package com.woshen.acl.mapper;

import com.woshen.acl.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}
