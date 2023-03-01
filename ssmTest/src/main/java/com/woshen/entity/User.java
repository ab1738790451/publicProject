package com.woshen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.List;

import com.woshen.common.baseTempl.BaseEntity;
import com.woshen.common.constants.UserType;
import com.woshen.common.webcommon.annotation.EnableEncryption;
import com.woshen.common.webcommon.annotation.EncryptionField;
import com.woshen.common.webcommon.model.DataStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@EnableEncryption
public class User extends BaseEntity<Integer> {


    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户账号
     */
    @TableField("login_id")
    private String loginId;

    /**
     * 手机号
     */
    @TableField("mobile_phone")
    @EncryptionField
    private String mobilePhone;

    /**
     * 昵称
     */
    @TableField("nicke_name")
    private String nickeName;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 头像
     */
    @TableField("head_img")
    private String headImg;

    /**
     * 用户状态
     */
    private DataStatus status;

    /**
     * 加密的密码
     */
    private String password;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private UserType userType;

    @TableField(exist = false)
    private List<String> appIds;

    @TableField(exist = false)
    private List<String> roleIds;

    @Override
    public Integer getPk() {
        return id;
    }
}
