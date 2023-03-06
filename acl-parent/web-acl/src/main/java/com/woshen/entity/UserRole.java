package com.woshen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.woshen.common.baseTempl.BaseEntity;
import com.woshen.common.constants.UserType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class UserRole extends BaseEntity<Integer> {


    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色类型 超级管理员 、普通管理员 、普通用户
     */
    @TableField("user_type")
    private UserType userType;

    /**
     * 用户角色id
     */
    @TableField("role_ids")
    private String roleIds;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 应用id
     */
    private String appIds;

    @Override
    public Integer getPk() {
        return id;
    }
}
