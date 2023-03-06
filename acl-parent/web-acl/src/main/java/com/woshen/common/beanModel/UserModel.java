package com.woshen.common.beanModel;

import lombok.Data;

/**
*@company woshen
*@author liuhaibo
*@Date 2022/3/9 0:37
*@version 1.0
*@description
*/
@Data
public class UserModel {
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

    private String Lj;

    private boolean lf;

}
