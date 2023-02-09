package com.woshen.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
*@company woshen
*@author liuhaibo
*@Date 2022/11/15 23:22
*@version 1.0
*@description
*/
@Data
public class User {
    /**
     * 用户uid
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 登录账号
     */
    private String loginId;
    /**
     * 手机号
     */
    private Integer mobilePhone;
    /**
     * 昵称
     */
    private String nickeName;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String headImg;

    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
