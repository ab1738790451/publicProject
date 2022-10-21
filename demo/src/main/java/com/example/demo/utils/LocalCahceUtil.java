package com.example.demo.utils;

import com.example.demo.utils.TaskUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/5/31 15:21
 * @Version: 1.0.0
 * @Description: 本地缓存
 */
@Component
public class LocalCahceUtil {

   private static Map<String, CacheEntity<List<String>>> listCaheMap = new ConcurrentHashMap<>();

   private static Map<String,CacheEntity<Map<String,String>>> hashCacheMap = new ConcurrentHashMap<>();

   private static Map<String, CacheEntity<Set<String>>> setCacheMap = new ConcurrentHashMap<>();

   private static Map<String, CacheEntity<String>> cacheMap = new ConcurrentHashMap<>();

   private LocalCahceUtil(){}

    /**
     * 每隔30秒清理过期数据
     */
   static {
        TaskUtil.scheduleAtFixedRateExec(() -> {
            //删除过期的listCaheMap数据
            deletedTimeOutData((Map) listCaheMap);
        },0,15,TimeUnit.SECONDS);

        TaskUtil.scheduleAtFixedRateExec(() -> {
            //删除过期的hashCacheMap数据
            deletedTimeOutData((Map) hashCacheMap);
        },0,15,TimeUnit.SECONDS);

        TaskUtil.scheduleAtFixedRateExec(() -> {
            //删除过期的setCacheMap数据
            deletedTimeOutData((Map) setCacheMap);
        },0,15,TimeUnit.SECONDS);

        TaskUtil.scheduleAtFixedRateExec(() -> {
            //删除过期的cacheMap数据
            deletedTimeOutData((Map) cacheMap);
        },0,15,TimeUnit.SECONDS);
   }


   private static void deletedTimeOutData(Map<String,CacheEntity> cacheMap){
       List<String> cacheKey  = new ArrayList<>();
       for (Map.Entry item: cacheMap.entrySet()
       ) {
           CacheEntity value = (CacheEntity)item.getValue();
           if(value.timeOut() ){
               cacheKey.add((String) item.getKey());
           }
       }
       cacheKey.stream().forEach( t -> cacheMap.remove(t));
   }


    /**
     * 在list的头部插入一条数据
     * @param key
     * @param value
     * @param ttl 存活时间
     */
    public static void lpush(String key,String value,int ttl){
        lpushAndExtTime(key,value,ttl,false);
    }

    /**
     * 在list的头部插入一条数据
     * @param key
     * @param value
     * @param ttl 存活时间
     * @param extendedTtl 是否续时
     */
   public static void lpushAndExtTime(String key,String value,int ttl,boolean extendedTtl){
       CacheEntity<List<String>> listCacheEntity = listCaheMap.get(key);
       if(listCacheEntity == null){
           synchronized (listCaheMap){
               if(listCaheMap.get(key) == null){
                   listCacheEntity = new CacheEntity<>(new ArrayList<>(), LocalDateTime.now(), ttl);
                   listCaheMap.put(key,listCacheEntity);
               }else{
                   listCacheEntity = listCaheMap.get(key);
               }
           }
       }
       if(listCacheEntity.timeOut()){
           return;
       }
       if(extendedTtl) {
           listCacheEntity.setTtl(listCacheEntity.getTtl() + ttl);
       }
       List<String> list = listCacheEntity.getValue();
        list.add(0,value);
   }

    /**
     * 在list尾部追加一条数据
     * @param key
     * @param value
     * @param ttl 存活时间
     */
    public static void rpush(String key,String value,int ttl){
        rpushAndExtT(key,value,ttl,false);
    }
    /**
     * 在list尾部追加一条数据
     * @param key
     * @param value
     * @param ttl 存活时间
     * @param extendedTtl 是否续时
     */
    public static void rpushAndExtT(String key,String value,int ttl,boolean extendedTtl){
        CacheEntity<List<String>> listCacheEntity = listCaheMap.get(key);
        if(listCacheEntity == null){
            synchronized (listCaheMap){
                if(listCaheMap.get(key) == null){
                    listCacheEntity = new CacheEntity<>(new ArrayList<>(), LocalDateTime.now(), ttl);
                    listCaheMap.put(key,listCacheEntity);
                }else{
                    listCacheEntity = listCaheMap.get(key);
                }
            }
        }
        if(listCacheEntity.timeOut()){
            return;
        }
        if(extendedTtl) {
            listCacheEntity.setTtl(listCacheEntity.getTtl() + ttl);
        }
        List<String> list = listCacheEntity.getValue();
        list.add(value);
    }

    /**
     * 使list的第一条数据出栈并返回出栈的数据
     * @param key
     * @return
     */
    public static String lpop(String key){
        CacheEntity<List<String>> listCacheEntity = listCaheMap.get(key);
        if(listCacheEntity == null){
            return null;
        }
        synchronized (listCaheMap){
            if(listCacheEntity.timeOut()){
                return null;
            }
            List<String> value = listCacheEntity.getValue();
            if(value.size() < 1){
                return null;
            }
            return value.remove(0);
        }

    }

    /**
     * 使list的最后一条数据出栈并返回出栈的数据
     * @param key
     * @return
     */
    public static String rpop(String key){
        CacheEntity<List<String>> listCacheEntity = listCaheMap.get(key);
        if(listCacheEntity == null){
            return null;
        }
        synchronized (listCaheMap){
            if(listCacheEntity.timeOut()){
                return null;
            }
            List<String> value = listCacheEntity.getValue();
            if(value.size() < 1){
                return null;
            }
            return  value.remove(value.size() - 1);
        }

    }

    /**
     * 删除 {key}对应的list
     * @param key
     */
    public static void listDeleted(String key){
        CacheEntity<List<String>> listCacheEntity = listCaheMap.get(key);
        if (listCacheEntity != null && !listCacheEntity.timeOut()) {
             listCacheEntity.setDeathTime(LocalDateTime.now());
        }
    }

    /**
     * 获取{key}对应hash中键为{id}的值
     * @param key
     * @param id
     * @return
     */
    public static String hget(String key,String id){
        CacheEntity<Map<String, String>> mapCacheEntity = hashCacheMap.get(key);
        if(mapCacheEntity == null || mapCacheEntity.timeOut()){
            return null;
        }
         return mapCacheEntity.getValue().get(id);
    }


    /**
     * 为{key}对应的hash新增一个键值对，或者改变键值对的对应关系
     * @param key
     * @param id
     * @param value
     * @param ttl 存活时间
     */
    public static void hset(String key,String id,String value,int ttl){
        hsetAndExtT(key,id,value,ttl,false);
    }

    /**
     * 为{key}对应的hash新增一个键值对，或者改变键值对的对应关系
     * @param key
     * @param id
     * @param value
     * @param ttl 存活时间
     * @param extendedTtl 是否续时
     */
    public static void hsetAndExtT(String key,String id,String value,int ttl,boolean extendedTtl){
        CacheEntity<Map<String, String>> mapCacheEntity = hashCacheMap.get(key);
        if(mapCacheEntity == null){
            synchronized (hashCacheMap){
                if(hashCacheMap.get(key) == null){
                    mapCacheEntity = new  CacheEntity(new HashMap<>(),LocalDateTime.now(),ttl);
                    hashCacheMap.put(key,mapCacheEntity);
                }else{
                    mapCacheEntity = hashCacheMap.get(key);
                }
            }
        }
         if(mapCacheEntity.timeOut()){
             return;
         }
         if(extendedTtl){
             mapCacheEntity.setTtl(mapCacheEntity.getTtl() + ttl);
         }
        Map<String, String> map = mapCacheEntity.getValue();
        map.put(id,value);
    }

    /**
     * 从{key}对应的hash中删除键值对
     * @param key
     * @param id
     */
    public static void hremove(String key,String id){
        CacheEntity<Map<String, String>> mapCacheEntity = hashCacheMap.get(key);
        if(mapCacheEntity != null){
            mapCacheEntity.getValue().remove(id);
        }

    }

    /**
     * 删除{key}对应的hash
     * @param key
     */
    public static void hdeleted(String key){
        CacheEntity<Map<String, String>> mapCacheEntity = hashCacheMap.get(key);
        if(mapCacheEntity != null && !mapCacheEntity.timeOut()){
            mapCacheEntity.setDeathTime(LocalDateTime.now());
        }

    }


    /**
     * 往{key}对应的set中添加一个{value}
     * @param key
     * @param value
     * @param ttl 存活时间
     */
    public static void sset(String key,String value,int ttl){
      ssetAndExtT(key,value,ttl,false);
    }


    /**
     * 往{key}对应的set中添加一个{value}
     * @param key
     * @param value
     * @param ttl 存活时间
     * @param extendedTtl 是否续时
     */
    public static void ssetAndExtT(String key,String value,int ttl,boolean extendedTtl){
        CacheEntity<Set<String>> setCacheEntity = setCacheMap.get(key);
        if(setCacheEntity == null){
            synchronized (setCacheMap){
                if(setCacheMap.get(key) == null){
                    setCacheEntity = new CacheEntity<>(new HashSet<>(),LocalDateTime.now(),ttl);
                    setCacheMap.put(key,setCacheEntity);
                }else{
                    setCacheEntity = setCacheMap.get(key);
                }
            }
        }
        if(setCacheEntity.timeOut()){
            return;
        }
        if(extendedTtl){
            setCacheEntity.setTtl(setCacheEntity.getTtl() + ttl);
        }
        setCacheEntity.getValue().add(value);
    }

    /**
     * 获取set的所有值
     * @param key
     * @return
     */
    public static List<String> sgetAll(String key){
        CacheEntity<Set<String>> setCacheEntity = setCacheMap.get(key);
        if(setCacheEntity == null || setCacheEntity.timeOut()){
            return null;
        }
        return new ArrayList<>(setCacheEntity.getValue());
    }

    /**
     * set中是否包含{value}
     * @param key
     * @param value
     * @return
     */
    public static boolean contain(String key,String value){
        CacheEntity<Set<String>> setCacheEntity = setCacheMap.get(key);
        if(setCacheEntity != null && !setCacheEntity.timeOut()){
            return setCacheEntity.getValue().contains(value);
        }
        return false;
    }

    /**
     * 从set中移除{value}
     * @param key
     * @param value
     * @return
     */
    public static boolean sremove(String key,String value){
        CacheEntity<Set<String>> setCacheEntity = setCacheMap.get(key);
        if(setCacheEntity !=  null){
            return setCacheEntity.getValue().remove(value);
        }
        return true;
    }

    /**
     * 删除set
     * @param key
     */
    public static void sdeleted(String key){
        CacheEntity<Set<String>> setCacheEntity = setCacheMap.get(key);
        if(setCacheEntity != null){
            if(!setCacheEntity.timeOut()){
                setCacheEntity.setDeathTime(LocalDateTime.now());
            }
        }
    }

    /**
     * 创建或者修改一个字符串到缓存中
     * @param key
     * @param value
     * @param ttl 存活时间
     */
    public static void set(String key,String value,int ttl){
        setAndExtT(key,value,ttl,false);
    }


    /**
     * 创建或者修改一个字符串到缓存中
     * @param key
     * @param value
     * @param ttl 存活时间
     * @param extendedTtl 是否续时
     */
    public static void setAndExtT(String key,String value,int ttl,boolean extendedTtl){

            synchronized (cacheMap){
                CacheEntity<String> stringCacheEntity = cacheMap.get(key);
                if(stringCacheEntity == null){
                    stringCacheEntity = new CacheEntity<>(value,LocalDateTime.now(),ttl);
                    cacheMap.put(key,stringCacheEntity);
                }else{
                    if(stringCacheEntity.timeOut()){
                        return;
                    }
                    if(extendedTtl){
                        stringCacheEntity.setTtl(stringCacheEntity.getTtl() + ttl);
                    }
                    stringCacheEntity.setValue(value);
                }
            }
    }

    /**
     * 获取{key}对应的字符串的值
     * @param key
     * @return
     */
    public static String get(String key){
        CacheEntity<String> stringCacheEntity = cacheMap.get(key);
        if(stringCacheEntity != null){
            if(!stringCacheEntity.timeOut()) {
                return stringCacheEntity.getValue();
            }
        }
        return null;
    }

    /**
     * 删除{key}对应得字符串
     * @param key
     */
    public static void deleted(String key){
        CacheEntity<String> stringCacheEntity = cacheMap.get(key);
        if(stringCacheEntity != null){
            if(!stringCacheEntity.timeOut()){
                stringCacheEntity.setDeathTime(LocalDateTime.now());
            }
        }
    }

    /**
     * 用来表示缓存节点的类
     * @param <T>
     */
   static class CacheEntity<T>{
       private T value;

       private LocalDateTime createTime;

       private int ttl;

       private LocalDateTime deathTime;

       public CacheEntity(T value,LocalDateTime createTime,int ttl){
           this.value = value;
           this.createTime = createTime;
           this.ttl = ttl;
           this.deathTime = ttl < 1? null: createTime.plusSeconds(ttl);
       }

       public T getValue() {
           return value;
       }

       public void setValue(T value) {
           this.value = value;
       }

       public LocalDateTime getCreateTime() {
           return createTime;
       }

       public void setCreateTime(LocalDateTime createTime) {
           this.createTime = createTime;
       }

       public int getTtl() {
           return ttl;
       }

       public void setTtl(int ttl) {
           this.ttl = ttl;
           setDeathTime(ttl < 1? null: createTime.plusSeconds(ttl));
       }

       public LocalDateTime getDeathTime() {
           return deathTime;
       }

       public void setDeathTime(LocalDateTime deathTime) {
           if(!timeOut()){
               this.deathTime = deathTime;
           }
       }
       public boolean timeOut(){
           return getDeathTime() == null?false: getDeathTime().isBefore(LocalDateTime.now());
       }
   }
}
