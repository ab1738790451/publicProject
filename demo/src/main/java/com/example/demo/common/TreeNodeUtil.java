package com.example.demo.common;

import java.util.*;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/6/21 11:41
 * @Version: 1.0.0
 * @Description: 描述
 */
public class TreeNodeUtil<T extends TreeNode> {

    private List<T> treeDatas = new ArrayList<>();

    private Integer parentId;

    private Map<Integer,List<T>> treeMap = new HashMap<>();

    private Map<Integer,T> dataMap = new HashMap<>();

    public  TreeNodeUtil(List<T> treeNodes){
       init(treeNodes,-1);
    }

    public  TreeNodeUtil(List<T> treeNodes,Integer parentId){
        init(treeNodes,parentId);
    }

    private void init(List<T> treeNodes,Integer parentId){
        this.parentId = parentId == null?-1:parentId;
        if(treeNodes == null || treeNodes.isEmpty() ){
            return;
        }
        assemblyTree(treeNodes);
    }

    private void assemblyTree(List<T> treeNodes){
        Iterator<T> iterator = treeNodes.iterator();
        while (iterator.hasNext()){
            T next = iterator.next();
            Integer parentId = next.getParent();

            //加入父节点,根节点无父节点
            if(parentId != this.parentId.intValue()){
                List<T> parentChildrens = treeMap.get(parentId);
                if(parentChildrens == null){
                    parentChildrens = new ArrayList<>();
                    treeMap.put(parentId,parentChildrens);
                }
                parentChildrens.add(next);
            }
            //创建id与数据映射关系
            dataMap.put(next.getNodeId(),next);
        }

        Iterator<Map.Entry<Integer, List<T>>> treeIterator = treeMap.entrySet().iterator();
        while (treeIterator.hasNext()){
            Map.Entry<Integer, List<T>> next = treeIterator.next();
            //与子节点关联
            Integer key = next.getKey();
            List<T> value = next.getValue();
            T treeNode = dataMap.get(key);
            treeNode.setChildren(value);
            //根节点处理
            if(isRoot(treeNode)){
                treeDatas.add(treeNode);
            }
        }
    }


    public boolean isRoot(T node){
         if(node.getNodeId() == this.parentId.intValue()){
             return true;
         }
        T parentNode = dataMap.get(node.getParent());
        if(parentNode == null ){
             return true;
         }
         return false;
    }

    public List<T> getTreeDatas() {
        return treeDatas;
    }

    public List<T> getChilren(Integer id){
         return treeMap.get(id);
    }

    public T getParentNode(Integer id){
        T t = dataMap.get(id);
        return t == null?null:dataMap.get(t.getParent());
    }
 }
