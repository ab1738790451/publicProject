package com.woshen.service;

import com.woshen.entity.User;

import java.util.List;

/**
 * @author liuhaibo
 * @version 1.0
 * @company woshen
 * @Date 2022/11/15 23:35
 * @description
 */
public interface UserService {

    void insert(User user);

    List<User> selectAll();

    User selectByLoginId(String loginId);

    User selectByUserId(String userId);
}
