package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/6/7 13:37
 * @Version: 1.0.0
 * @Description: 描述
 */
@Data
public class MemberUserVo extends MemberUser {
    /**
     * 会员购买次数
     */
    private Integer byCount;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户状态
     */
    private String userStatus;

    /**
     * 查询开始时间
     */
    private Date startDate;

    /**
     * 查询结束时间
     */
    private Date endDate;
}
