package com.woshen.common.beanModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @Author: liuhaibo
 * @Date: 2023/2/12 17:09
 * @Version: 1.0.0
 * @Description: 描述
 */
public class BeanUtils {

    private BeanUtils(){}
    /**
     * 将对象转换为map
     * @param menu
     * @return
     */
    public static Map<String,Object> beanToMap(Object menu){
        if(menu == null){
            return null;
        }
        Map<String,Object> resultMap = new HashMap<>();
        Class<?> aClass = menu.getClass();
        String packageName = aClass.getPackage() == null?"":aClass.getPackage().getName();
        getFiledValues(aClass,menu,resultMap,new ArrayList<>(),true,packageName);
        return resultMap;
    }

    /**
     * 转换处理核心方法
     * @param acls 当前类型
     * @param value 初始对象
     * @param resultMap 返回结果
     * @param exclude 需要排除或处理过的字段
     * @param isSource 是否是原始对象
     * @param sourcePackage 原始包名
     */
    private  static  void getFiledValues(Class<?> acls, Object value, Map<String,Object> resultMap, List<String> exclude, boolean isSource, String sourcePackage){
        Field[] fields = acls.getDeclaredFields();
        String packageName = acls.getPackage() == null?"":acls.getPackage().getName();
        Set<String> fieldList = new HashSet<>();
        if( fields.length > 0){
            for (Field field:fields
            ) {
                fieldList.add(field.getName());
                if(!isSource) {
                    if(Modifier.isPrivate(field.getModifiers())){
                        exclude.add(field.getName());
                    }
                    if (!sourcePackage.equals(packageName) && field.getModifiers() == 0) {
                        exclude.add(field.getName());
                    }
                }
            }
        }

        try {
            //处理get方法
            Method[] methods = acls.getMethods();
            if(methods != null && methods.length > 0){
                String[] empty = new String[0];
                for (Method method:methods
                ) {
                    String name = method.getName();
                    if(name.equals("getClass")){
                        continue;
                    }
                    if(name.startsWith("is") && method.getReturnType().getTypeName().equals(boolean.class.getTypeName()) && name.length() > 2){
                        Object fieldValue = method.invoke(value,empty);
                        String fieldName = name.substring(2);
                        //大写开头的字段
                        if(!fieldList.contains(fieldName)){
                            fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
                        }
                        exclude.add(fieldName);
                        resultMap.put(fieldName,fieldValue);
                    }else if(name.startsWith("get") && name.length() > 3){
                        Object fieldValue = method.invoke(value,empty);
                        String fieldName = name.substring(3);
                        //大写开头的字段
                        if(!fieldList.contains(fieldName)){
                            fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
                        }
                        exclude.add(fieldName);
                        resultMap.put(fieldName,fieldValue);
                    }
                }
            }
            //没有get方法的字段处理
            for (Field field:fields
            ) {
                Class<? extends Field> cls = field.getClass();
                String name = field.getName();
                if(exclude.contains(name) || name.equals("class")){
                    continue;
                }
                field.setAccessible(true);
                resultMap.put(name,field.get(value));
                field.setAccessible(false);
            }

            Class<?> superclass = acls.getSuperclass();
            if(superclass !=null  &&  !superclass.getTypeName().equals(Object.class.getTypeName())){
                getFiledValues(superclass,value,resultMap,exclude,false,sourcePackage);
            }
            Class<?>[] interfaces = acls.getInterfaces();
            if(interfaces != null && interfaces.length > 0){
                for (Class<?> in:interfaces
                ) {
                    getFiledValues(in,value,resultMap,exclude,false,sourcePackage);
                }
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

}
