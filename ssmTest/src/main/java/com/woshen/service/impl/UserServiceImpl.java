package com.woshen.service.impl;

import com.woshen.entity.User;
import com.woshen.mapper.UserMapper;
import com.woshen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*@company woshen
*@author liuhaibo
*@Date 2022/11/15 23:35
*@version 1.0
*@description
*/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public User selectByLoginId(String loginId) {
        return userMapper.selectByLoginId(loginId);
    }

    @Override
    public User selectByUserId(String userId) {
        return userMapper.selectByUserId(userId);
    }
}
