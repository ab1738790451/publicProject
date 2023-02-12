package com.woshen.service.impl;

import com.woshen.common.baseTempl.BaseServiceImpl;
import com.woshen.entity.User;
import com.woshen.mapper.UserMapper;
import com.woshen.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<Integer, UserMapper, User> implements IUserService {

}
