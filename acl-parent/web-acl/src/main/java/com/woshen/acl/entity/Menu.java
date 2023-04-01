package com.woshen.acl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.woshen.common.base.model.BaseTreeNode;
import com.woshen.common.webcommon.db.entity.BaseEntity;
import com.woshen.common.webcommon.model.Bool;
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

    private Integer appId;

    private String title;

    private String url;

    private Bool isMenu;

    private Integer parentId;

    private Integer priority;

    private DataStatus status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField(exist = false)
    private Boolean checked;

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

    public String getIsMenuStr(){
        return isMenu == null?null:isMenu.getDesc();
    }

    public String getCreateTimeStr(){
        return createTime == null?null: createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getUpdateTimeStr(){
        return updateTime == null?null: updateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public Integer getPk() {
        return id;
    }
}
