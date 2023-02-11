package com.woshen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.woshen.common.base.model.TreeNode;
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
public class Menu extends TreeNode<Menu> implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String url;

    @TableField("parent_id")
    private Integer parentId;

    private String status;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;


    @Override
    public Integer getParent() {
        return this.parentId;
    }

    @Override
    public Integer getNodeId() {
        return this.id;
    }
}
