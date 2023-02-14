package com.woshen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.woshen.common.baseTempl.BaseEntity;
import com.woshen.common.webcommon.model.DataStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class App extends BaseEntity<Integer> {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 数据状态
     */
    private DataStatus status;

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


    @Override
    public Integer getPk() {
        return this.id;
    }
}
