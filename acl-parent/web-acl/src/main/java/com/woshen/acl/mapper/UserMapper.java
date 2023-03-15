package com.woshen.acl.mapper;

import com.woshen.acl.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
