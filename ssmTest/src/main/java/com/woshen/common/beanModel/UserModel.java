package com.woshen.common.beanModel;/**
*@company woshen
*@author liuhaibo
*@Date 2022/3/9 0:37
*@version 1.0
*@description
*/
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Integer getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(Integer mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getNickeName() {
        return nickeName;
    }

    public void setNickeName(String nickeName) {
        this.nickeName = nickeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
