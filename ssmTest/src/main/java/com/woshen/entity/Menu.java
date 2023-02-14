package com.woshen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.List;
import com.woshen.common.base.model.BaseTreeNode;
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
public class Menu extends BaseEntity<Integer> implements  BaseTreeNode<Menu> {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String appId;

    private String title;

    private String url;

    private String isMenu;

    private Integer parentId;

    private DataStatus status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @Override
    public Integer getParent() {
        return this.parentId;
    }

    @Override
    public Integer getNodeId() {
        return this.id;
    }
    @TableField(exist = false)
    private List<Menu> children;

    @Override
    public List<Menu> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    @Override
    public Integer getPk() {
        return id;
    }
}
