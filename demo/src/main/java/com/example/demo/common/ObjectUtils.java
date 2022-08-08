package com.example.demo.common;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/4/27 11:51
 * @Version: 1.0.0
 * @Description: String 工具类
 */
public class ObjectUtils {

   private ObjectUtils(){}
    /**
     * 输出toString字符串
     * @param o 原对象
     * @return 字符串
     */
   public static String ObjectToString(Object o){
       StringBuffer s = new StringBuffer("{");
       List<Field> fieldList = new LinkedList<>();
       Class<?> aClass = o.getClass();
       Field[] flds = aClass.getFields();
       Field[] dflds = aClass.getDeclaredFields();
       //获取非public字段
       for (Field df:dflds
       ) {
           if(Modifier.isPublic(df.getModifiers())){
               continue;
           }
           fieldList.add(df);
       }
       String packageName = (aClass.getPackage() == null) ? "" : aClass.getPackage().getName();
       Class<?> superclass = aClass.getSuperclass();
       Field[] declaredFields1 = superclass.getDeclaredFields();
       String superPackageName = null;
       //获取继承自父类字段
       while (superclass.getSuperclass() != null && declaredFields1.length > 0 && !superclass.getTypeName().equals("java.lang.Object")){
             superPackageName = (superclass.getPackage() == null) ? "" : superclass.getPackage().getName();
           for (Field dlf:declaredFields1
           ) {
               //父类私有属性子类不继承
               if( Modifier.isPrivate(dlf.getModifiers())) {
                   continue;
               }
               //非同包父类默认属性子类不继承
               if(dlf.getModifiers() == 0 && !packageName.equals(superPackageName)){
                   continue;
               }
               fieldList.add(dlf);
           }
           superclass = superclass.getSuperclass();
           declaredFields1 = superclass.getDeclaredFields();
       }

       try {
           for (Field sf:fieldList){
               sf.setAccessible(true);
               s.append(sf.getName() + "=" + (sf.get(o) == null ? null : sf.get(o).toString()) +",");
           }
           for (Field f:flds
           ) {
               f.setAccessible(true);
               s.append(f.getName() + "=" + (f.get(o) == null ? null : f.get(o).toString()) +",");
           }
       } catch (IllegalAccessException e) {
           e.printStackTrace();
       }
       return s.substring(0,s.length()-1)+"}";
    }

    /**
     * 深度克隆对象
     * @param t 被克隆对象
     * @param <T> 克隆对象类型
     * @return 克隆对象
     */
    public static <T> T getCloneObject(T t) throws IOException, ClassNotFoundException {
            Object o = null;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(t);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            o = objectInputStream.readObject();
        return (T)o;
    }
}
