package com.example.demo.common;

import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/6/21 11:32
 * @Version: 1.0.0
 * @Description: 描述
 */
public abstract class TreeNode<T> {

    private List<T> children;

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public abstract Integer getParent();

    public abstract Integer getNodeId();



}
