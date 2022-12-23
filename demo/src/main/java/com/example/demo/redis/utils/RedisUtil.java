package com.example.demo.redis.utils;

import com.example.demo.config.SpringUtils;
import com.example.demo.redis.core.*;
import com.example.demo.redis.constants.RedisKeyType;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.util.CollectionUtils;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/16 16:01
 * @Version: 1.0.0
 * @Description: 描述
 */
public class RedisUtil {

    private static RedisTemplate<String,String> redisTemplate = SpringUtils.getBean("redisTemplate",RedisTemplate.class);

    private static StringExecutor stringExecutor = new StringExecutor();

    private static SetExecutor setExecutor = new SetExecutor();

    private static ListExecutor listExecutor = new ListExecutor();

    private static HashExecutor hashExecutor = new HashExecutor();

    private static ZSetExecutor zSetExecutor = new ZSetExecutor();

    private RedisUtil(){}
    /**
     * 字符串操作调用
     */
    public static StringExecutor stringExecutor(){
        return stringExecutor;
    }
    /**
     * 集合操作调用
     */
    public static SetExecutor SetExecutor(){
        return setExecutor;
    }
    /**
     * 链表操作调用
     */
    public static ListExecutor listExecutor(){
        return listExecutor;
    }
    /**
     * 哈希操作调用
     */
    public static HashExecutor hashExecutor(){
        return hashExecutor;
    }
    /**
     * 有序集合操作调用
     */
    public static ZSetExecutor zSetExecutor(){
        return zSetExecutor;
    }

    private static  String getKeyStr(RedisKeyNs key, Serializable id, RedisKeyType redisKeyType){
        if(key == null || id == null){
            throw new RuntimeException("the redis key and id  is not allow null");
        }
        if(redisKeyType != null && !redisKeyType.equals(key.getKeyType())){
            throw new RuntimeException("the redis key type does not match the declared one");
        }
        return key.getNameSpace() + id.toString();
    }

    /**
     * 根据 {@key} 获取生存时间
     * @param key key 前缀
     * @param id key标识
     * @return
     */
    public static  long getExpire(RedisKeyNs key,Serializable id){
        String keyStr = getKeyStr(key,id,null);
        return redisTemplate.getExpire(keyStr);
    }

    /**
     * 字符串操作类，包含对字符串类型的各种操作方法
     */
    public static class StringExecutor implements RedisStringOperations{

        @Override
        public void set(RedisKeyNs key, Serializable id, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.STRING);
            long expire = key.getExpire();
            if(expire <= 0){
                redisTemplate.opsForValue().set(keyStr,value);
            }else{
                redisTemplate.opsForValue().set(keyStr,value,expire, TimeUnit.SECONDS);
            }
        }

        @Override
        public Boolean setIfAbsent(RedisKeyNs key, Serializable id, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.STRING);
            long expire = key.getExpire();
            if(expire <= 0){
               return redisTemplate.opsForValue().setIfAbsent(keyStr,value);
            }else{
               return redisTemplate.opsForValue().setIfAbsent(keyStr,value,expire, TimeUnit.SECONDS);
            }
        }

        @Override
        public Boolean setIfPresent(RedisKeyNs key, Serializable id, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.STRING);
            long expire = key.getExpire();
            if(expire <= 0){
                return redisTemplate.opsForValue().setIfPresent(keyStr,value);
            }else{
                return redisTemplate.opsForValue().setIfPresent(keyStr,value,expire, TimeUnit.SECONDS);
            }
        }

        @Override
        public String get(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.STRING);
            return  redisTemplate.opsForValue().get(keyStr);
        }

        @Override
        public String getAndSet(RedisKeyNs key, Serializable id, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.STRING);
            long expire = key.getExpire();
            if(expire <= 0){
                return redisTemplate.opsForValue().getAndSet(keyStr,value);
            }else{
                String[] result = new String[1];
                ValueOperations<String, String> operations = redisTemplate.opsForValue();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        result[0] = operations.getAndSet(keyStr,value);
                        operations.getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return result[0];
            }
        }

        @Override
        public Long increment(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.STRING);
            long expire = key.getExpire();
            if(expire <= 0){
                return redisTemplate.opsForValue().increment(keyStr);
            }else{
                Long[] size = new Long[1];
                ValueOperations<String, String> operations = redisTemplate.opsForValue();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        size[0] = operations.increment(keyStr);
                        operations.getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return size[0];
            }
        }

        @Override
        public Long increment(RedisKeyNs key, Serializable id, long delta) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.STRING);
            long expire = key.getExpire();
            if(expire <= 0){
                return redisTemplate.opsForValue().increment(keyStr,delta);
            }else{
                Long[] sum = new Long[1];
                ValueOperations<String, String> operations = redisTemplate.opsForValue();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        sum[0] = operations.increment(keyStr,delta);
                        operations.getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return sum[0];
            }
        }

        @Override
        public Double increment(RedisKeyNs key, Serializable id, double delta) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.STRING);
            long expire = key.getExpire();
            if(expire <= 0){
                return redisTemplate.opsForValue().increment(keyStr,delta);
            }else{
                Double[] sum = new Double[1];
                ValueOperations<String, String> operations = redisTemplate.opsForValue();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        sum[0] = operations.increment(keyStr,delta);
                        operations.getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return sum[0];
            }
        }

        @Override
        public Long decrement(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.STRING);
            long expire = key.getExpire();
            if(expire <= 0){
                return redisTemplate.opsForValue().decrement(keyStr);
            }else{
                Long[] sum = new Long[1];
                ValueOperations<String, String> operations = redisTemplate.opsForValue();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        sum[0] = operations.decrement(keyStr);
                        operations.getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return sum[0];
            }
        }

        @Override
        public Long decrement(RedisKeyNs key, Serializable id, long delta) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.STRING);
            long expire = key.getExpire();
            if(expire <= 0){
                return redisTemplate.opsForValue().decrement(keyStr,delta);
            }else{
                Long[] sum = new Long[1];
                ValueOperations<String, String> operations = redisTemplate.opsForValue();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        sum[0] = operations.decrement(keyStr,delta);
                        operations.getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return sum[0];
            }
        }

        @Override
        public Integer append(RedisKeyNs key, Serializable id, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.STRING);
            long expire = key.getExpire();
            if(expire <= 0){
                return redisTemplate.opsForValue().append(keyStr,value);
            }else{
                Integer[] sum = new Integer[1];
                ValueOperations<String, String> operations = redisTemplate.opsForValue();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        sum[0] = operations.append(keyStr,value);
                        operations.getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return sum[0];
            }
        }

        @Override
        public Long len(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.STRING);
            return redisTemplate.opsForValue().size(keyStr);
        }

    }

    /**
     * 集合操作类，包含对集合类型的各种操作方法
     */
    public static class SetExecutor implements RedisSetOperations {

        @Override
        public Long sadd(RedisKeyNs key, Serializable id, String... values) {
            String keyStr = getKeyStr(key, id, RedisKeyType.SET);
            long expire = key.getExpire();
            if (expire <= 0) {
                return redisTemplate.opsForSet().add(keyStr, values);
            } else {
                Long[] size = new Long[1];
                SetOperations<String, String> operations = redisTemplate.opsForSet();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        size[0] = operations.add(keyStr, values);
                        operations.getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return size[0];
            }
        }

        @Override
        public Long remove(RedisKeyNs key, Serializable id, Object... values) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.SET);
            long expire = key.getExpire();
            if (expire <= 0) {
                return redisTemplate.opsForSet().remove(keyStr, values);
            } else {
                Long[] size = new Long[1];
                SetOperations<String, String> operations = redisTemplate.opsForSet();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        size[0] = operations.remove(keyStr, values);
                        operations.getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return size[0];
            }
        }

        @Override
        public String pop(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.SET);
            long expire = key.getExpire();
            if (expire <= 0) {
                return redisTemplate.opsForSet().pop(keyStr);
            } else {
                String[] results = new String[1];
                SetOperations<String, String> operations = redisTemplate.opsForSet();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        results[0] = operations.pop(keyStr);
                        operations.getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return results[0];
            }
        }

        @Override
        public Set<String> pop(RedisKeyNs key, Serializable id, long count) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.SET);
            long expire = key.getExpire();
            if (expire <= 0) {
                return new HashSet<>(redisTemplate.opsForSet().pop(keyStr,count));
            } else {
                List[] results = new List[1];
                SetOperations<String, String> operations = redisTemplate.opsForSet();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        results[0] =redisTemplate.opsForSet().pop(keyStr,count);
                        operations.getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return new HashSet<>(results[0]);
            }
        }

        @Override
        public  Boolean move(RedisKeyNs key, Serializable id, String value, RedisKeyNs destKey, Serializable destId) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.SET);
            String destKeyStr =  getKeyStr(destKey,destId,RedisKeyType.SET);
            long expire = key.getExpire();
            if (expire <= 0) {
                return redisTemplate.opsForSet().move(keyStr,value,destKeyStr);
            } else {
                Boolean[] results = new Boolean[1];
                SetOperations<String, String> operations = redisTemplate.opsForSet();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        results[0] = redisTemplate.opsForSet().move(keyStr,value,destKeyStr);
                        operations.getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return results[0];
            }
        }

        @Override
        public Long ssize(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.SET);
            return  redisTemplate.opsForSet().size(keyStr);
        }

        @Override
        public Boolean isMember(RedisKeyNs key, Serializable id, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.SET);
            return  redisTemplate.opsForSet().isMember(keyStr,value);
        }

        @Override
        public Set<String> members(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.SET);
            return  redisTemplate.opsForSet().members(keyStr);
        }
    }

    /**
     * 链表操作类，包含对链表类型的各种操作方法
     */
    public static class ListExecutor implements RedisListOperations{

        private ListOperations<String, String> getListOperations(){
            return redisTemplate.opsForList();
        }

        @Override
        public List<String> range(RedisKeyNs key, Serializable id, long start, long end) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            return redisTemplate.opsForList().range(keyStr, start,end);
        }

        @Override
        public void trim(RedisKeyNs key, Serializable id, long start, long end) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            redisTemplate.opsForList().trim(keyStr, start,end);
        }

        @Override
        public Long size(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            return redisTemplate.opsForList().size(keyStr);
        }

        @Override
        public Long lpush(RedisKeyNs key, Serializable id, String... values){
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            long expire = key.getExpire();
            if (expire <= 0) {
                return getListOperations().leftPushAll(keyStr, values);
            } else {
                Long[] size = new Long[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        size[0] = getListOperations().leftPushAll(keyStr, values);
                        getListOperations().getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return size[0];
            }
        }

        @Override
        public Long leftPushAll(RedisKeyNs key, Serializable id, Collection<String> values) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            long expire = key.getExpire();
            if (expire <= 0) {
                return getListOperations().leftPushAll(keyStr, values);
            } else {
                Long[] size = new Long[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        size[0] = getListOperations().leftPushAll(keyStr, values);
                        getListOperations().getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return size[0];
            }
        }


        @Override
        public Long lPushIfPresent(RedisKeyNs key, Serializable id, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            long expire = key.getExpire();
            if (expire <= 0) {
                return getListOperations().leftPushIfPresent(keyStr, value);
            } else {
                Long[] size = new Long[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        size[0] = getListOperations().leftPushIfPresent(keyStr, value);
                        getListOperations().getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return size[0];
            }
        }

        @Override
        public Long rPush(RedisKeyNs key, Serializable id, String... values) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            long expire = key.getExpire();
            if (expire <= 0) {
                return getListOperations().rightPushAll(keyStr, values);
            } else {
                Long[] size = new Long[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        size[0] = getListOperations().rightPushAll(keyStr, values);
                        getListOperations().getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return size[0];
            }
        }


        @Override
        public Long rPushAll(RedisKeyNs key, Serializable id, Collection<String> values) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            long expire = key.getExpire();
            if (expire <= 0) {
                return getListOperations().rightPushAll(keyStr, values);
            } else {
                Long[] size = new Long[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        size[0] = getListOperations().rightPushAll(keyStr, values);
                        getListOperations().getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return size[0];
            }
        }


        @Override
        public Long rPushIfPresent(RedisKeyNs key, Serializable id, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            long expire = key.getExpire();
            if (expire <= 0) {
                return getListOperations().rightPushIfPresent(keyStr, value);
            } else {
                Long[] size = new Long[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        size[0] = getListOperations().rightPushIfPresent(keyStr, value);
                        getListOperations().getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return size[0];
            }
        }


        @Override
        public void set(RedisKeyNs key, Serializable id, long index, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            long expire = key.getExpire();
            if (expire <= 0) {
                getListOperations().set(keyStr, index,value);
            } else {
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        getListOperations().set(keyStr,index, value);
                        getListOperations().getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });

            }
        }

        @Override
        public Long remove(RedisKeyNs key, Serializable id, long count, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            long expire = key.getExpire();
            if (expire <= 0) {
                return  getListOperations().remove(keyStr, count,value);
            } else {
                Long[] size = new Long[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        size[0] = getListOperations().remove(keyStr, count,value);
                        getListOperations().getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return size[0];
            }
        }

        @Override
        public String index(RedisKeyNs key, Serializable id, long index) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            return  getListOperations().index(keyStr,index);
        }


        @Override
        public String lPop(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            long expire = key.getExpire();
            if (expire <= 0) {
                return  getListOperations().leftPop(keyStr);
            } else {
                String[] results = new String[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        results[0] = getListOperations().leftPop(keyStr);
                        getListOperations().getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return results[0];
            }
        }

        @Override
        public String rPop(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            long expire = key.getExpire();
            if (expire <= 0) {
                return  getListOperations().rightPop(keyStr);
            } else {
                String[] results = new String[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        results[0] = getListOperations().rightPop(keyStr);
                        getListOperations().getOperations().expire(keyStr, expire, TimeUnit.SECONDS);
                        return null;
                    }
                });
                return results[0];
            }
        }
    }

    /**
     * 哈希操作类，包含对哈希类型的各种操作方法
     */
    public static class HashExecutor implements RedisHashOperations{

        private  HashOperations<String, String, String> getOperations(){
            return redisTemplate.opsForHash();
        }

        @Override
        public Long delete(RedisKeyNs key, Serializable id, String... hashKeys) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            long expire = key.getExpire();
            if(expire <= 0){
                return getOperations().delete(keyStr,hashKeys);
            }else{
                Long[] sum = new Long[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        sum[0] = getOperations().delete(keyStr,hashKeys);
                        getOperations().getOperations().expire(keyStr,expire,TimeUnit.SECONDS);
                        return null;
                    }
                });
                return sum[0];
            }
        }

        @Override
        public Boolean hasKey(RedisKeyNs key, Serializable id, String hashKey) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            return getOperations().hasKey(keyStr,hashKey);
        }

        @Override
        public String get(RedisKeyNs key, Serializable id, String hashKey) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            return getOperations().get(keyStr,hashKey);
        }

        @Override
        public List<String> multiGet(RedisKeyNs key, Serializable id, Collection<String> hashKeys) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            return  getOperations().multiGet(keyStr,hashKeys);
        }

        @Override
        public Long increment(RedisKeyNs key, Serializable id, String hashKey, long delta) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            long expire = key.getExpire();
            if(expire <= 0){
                return getOperations().increment(keyStr,hashKey,delta);
            }else{
                Long[] sum = new Long[1];
                HashOperations<String, String, String> operations = getOperations();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                         sum[0] = operations.increment(keyStr, hashKey, delta);
                         operations.getOperations().expire(keyStr,expire,TimeUnit.SECONDS);
                        return null;
                    }
                });
                return sum[0];
            }
        }

        @Override
        public Double increment(RedisKeyNs key, Serializable id, String hashKey, double delta) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            long expire = key.getExpire();
            if(expire <= 0){
                return redisTemplate.opsForHash().increment(keyStr,hashKey,delta);
            }else{
                Double[] sum = new Double[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        sum[0] = getOperations().increment(keyStr, hashKey, delta);
                        getOperations().getOperations().expire(keyStr,expire,TimeUnit.SECONDS);
                        return null;
                    }
                });
                return sum[0];
            }
        }

        @Override
        public Set<String> keys(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            return getOperations().keys(keyStr);
        }

        @Override
        public Long lengthOfValue(RedisKeyNs key, Serializable id, String hashKey) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            return getOperations().lengthOfValue(keyStr,hashKey);
        }

        @Override
        public Long size(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            return getOperations().size(keyStr);
        }

        @Override
        public void putAll(RedisKeyNs key, Serializable id, Map<? extends String, ? extends String> m) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            long expire = key.getExpire();
            if(expire <= 0){
                redisTemplate.opsForHash().putAll(keyStr,m);
            }else{
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        getOperations().putAll(keyStr, m);
                        getOperations().getOperations().expire(keyStr,expire,TimeUnit.SECONDS);
                        return null;
                    }
                });
            }
        }

        @Override
        public void put(RedisKeyNs key, Serializable id, String hashKey, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            long expire = key.getExpire();
            if(expire <= 0){
                redisTemplate.opsForHash().put(keyStr,hashKey,value);
            }else{
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        getOperations().put(keyStr, hashKey, value);
                        getOperations().getOperations().expire(keyStr,expire,TimeUnit.SECONDS);
                        return null;
                    }
                });
            }
        }

        @Override
        public Boolean putIfAbsent(RedisKeyNs key, Serializable id, String hashKey, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            long expire = key.getExpire();
            if(expire <= 0){
                return redisTemplate.opsForHash().putIfAbsent(keyStr,hashKey,value);
            }else{
                Boolean[] results = new Boolean[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        results[0] = getOperations().putIfAbsent(keyStr, hashKey, value);
                        getOperations().getOperations().expire(keyStr,expire,TimeUnit.SECONDS);
                        return null;
                    }
                });
                return results[0];
            }
        }

        @Override
        public List<String> values(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            return getOperations().values(keyStr);
        }

        @Override
        public Map<String, String> entries(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            return getOperations().entries(keyStr);
        }
    }

    /**
     * 有序集合操作类，包含对有序集合类型的各种操作方法
     */
    public static class ZSetExecutor implements RedisZSetOperations{

        private  ZSetOperations<String, String> getOperations(){
            return redisTemplate.opsForZSet();
        }

        @Override
        public Boolean add(RedisKeyNs key, Serializable id, String value, double score) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            long expire = key.getExpire();
            if(expire <= 0){
                return getOperations().add(keyStr,value,score);
            }else{
                Boolean[] results = new Boolean[1];
                 redisTemplate.executePipelined(new RedisCallback<Object>() {
                     @Override
                     public Object doInRedis(RedisConnection connection) throws DataAccessException {
                         results[0] = getOperations().add(keyStr, value, score);
                         getOperations().getOperations().expire(keyStr,expire,TimeUnit.SECONDS);
                         return null;
                     }
                 });
                 return results[0];
            }
        }

        @Override
        public Long add(RedisKeyNs key, Serializable id, Set<ZSetDataModel> datas) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            if(CollectionUtils.isEmpty(datas)){
                throw new RuntimeException("datas is not be null");
            }
            Set<ZSetOperations.TypedTuple<String>>  typedTuples = new HashSet<>();
            datas.stream().forEach( t -> typedTuples.add(new DefaultTypedTuple<>(t.getValue(),t.getCore())));
            long expire = key.getExpire();
            if(expire <= 0){
                return getOperations().add(keyStr,typedTuples);
            }else{
                Long[] results = new Long[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        results[0] = getOperations().add(keyStr,typedTuples);
                        getOperations().getOperations().expire(keyStr,expire,TimeUnit.SECONDS);
                        return null;
                    }
                });
                return results[0];
            }
        }

        @Override
        public Long remove(RedisKeyNs key, Serializable id, String... values) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            long expire = key.getExpire();
            if(expire <= 0){
                return getOperations().remove(keyStr,values);
            }else{
                Long[] results = new Long[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        results[0] = getOperations().remove(keyStr, values);
                        getOperations().getOperations().expire(keyStr,expire,TimeUnit.SECONDS);
                        return null;
                    }
                });
                return results[0];
            }
        }

        @Override
        public Double incrementScore(RedisKeyNs key, Serializable id, String value, double delta) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            long expire = key.getExpire();
            if(expire <= 0){
                return getOperations().incrementScore(keyStr,value,delta);
            }else{
                Double[] results = new Double[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        results[0] = getOperations().incrementScore(keyStr,value,delta);
                        getOperations().getOperations().expire(keyStr,expire,TimeUnit.SECONDS);
                        return null;
                    }
                });
                return results[0];
            }
        }

        @Override
        public Long rank(RedisKeyNs key, Serializable id, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().rank(keyStr,value);
        }

        @Override
        public Long reverseRank(RedisKeyNs key, Serializable id, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().reverseRank(keyStr,value);
        }

        @Override
        public Set<String> range(RedisKeyNs key, Serializable id, long start, long end) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().range(keyStr,start,end);
        }

        @Override
        public Set<ZSetDataModel> rangeWithScores(RedisKeyNs key, Serializable id, long start, long end) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            Set<ZSetOperations.TypedTuple<String>> typedTuples = getOperations().rangeWithScores(keyStr, start, end);
            Set<ZSetDataModel> setDataModels = new HashSet<>();
            typedTuples.stream().forEach( t  -> setDataModels.add(new ZSetDataModel(t.getValue(),t.getScore())));
            return setDataModels;
        }

        @Override
        public Set<String> rangeByScore(RedisKeyNs key, Serializable id, double min, double max) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().rangeByScore(keyStr,min,max);
        }

        @Override
        public Set<ZSetDataModel> rangeByScoreWithScores(RedisKeyNs key, Serializable id, double min, double max) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            Set<ZSetOperations.TypedTuple<String>> typedTuples = getOperations().rangeByScoreWithScores(keyStr, min, max);
            Set<ZSetDataModel> setDataModels = new HashSet<>();
            typedTuples.stream().forEach( t  -> setDataModels.add(new ZSetDataModel(t.getValue(),t.getScore())));
            return setDataModels;
        }

        @Override
        public Set<String> rangeByScore(RedisKeyNs key, Serializable id, double min, double max, long offset, long count) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().rangeByScore(keyStr,min,max,offset,count);
        }

        @Override
        public Set<ZSetDataModel> rangeByScoreWithScores(RedisKeyNs key, Serializable id, double min, double max, long offset, long count) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            Set<ZSetOperations.TypedTuple<String>> typedTuples = getOperations().rangeByScoreWithScores(keyStr, min, max, offset, count);
            Set<ZSetDataModel> setDataModels = new HashSet<>();
            typedTuples.stream().forEach( t  -> setDataModels.add(new ZSetDataModel(t.getValue(),t.getScore())));
            return setDataModels;
        }

        @Override
        public Set<String> reverseRange(RedisKeyNs key, Serializable id, long start, long end) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().reverseRange(keyStr,start,end);
        }

        @Override
        public Set<ZSetDataModel> reverseRangeWithScores(RedisKeyNs key, Serializable id, long start, long end) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            Set<ZSetOperations.TypedTuple<String>> typedTuples = getOperations().reverseRangeWithScores(keyStr, start, end);
            Set<ZSetDataModel> setDataModels = new HashSet<>();
            typedTuples.stream().forEach( t  -> setDataModels.add(new ZSetDataModel(t.getValue(),t.getScore())));
            return setDataModels;
        }

        @Override
        public Set<String> reverseRangeByScore(RedisKeyNs key, Serializable id, double min, double max) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().reverseRangeByScore(keyStr,min,max);
        }

        @Override
        public Set<ZSetDataModel> reverseRangeByScoreWithScores(RedisKeyNs key, Serializable id, double min, double max) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            Set<ZSetOperations.TypedTuple<String>> typedTuples = getOperations().reverseRangeByScoreWithScores(keyStr, min, max);
            Set<ZSetDataModel> setDataModels = new HashSet<>();
            typedTuples.stream().forEach( t  -> setDataModels.add(new ZSetDataModel(t.getValue(),t.getScore())));
            return setDataModels;
        }

        @Override
        public Set<String> reverseRangeByScore(RedisKeyNs key, Serializable id, double min, double max, long offset, long count) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().reverseRangeByScore(keyStr,min,max,offset,count);
        }

        @Override
        public Set<ZSetDataModel> reverseRangeByScoreWithScores(RedisKeyNs key, Serializable id, double min, double max, long offset, long count) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            Set<ZSetOperations.TypedTuple<String>> typedTuples = getOperations().reverseRangeByScoreWithScores(keyStr, min, max, offset, count);
            Set<ZSetDataModel> setDataModels = new HashSet<>();
            typedTuples.stream().forEach( t  -> setDataModels.add(new ZSetDataModel(t.getValue(),t.getScore())));
            return setDataModels;
        }

        @Override
        public Long count(RedisKeyNs key, Serializable id, double min, double max) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().count(keyStr,min,max);
        }

        @Override
        public Long size(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().size(keyStr);
        }

        @Override
        public Long zCard(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().zCard(keyStr);
        }

        @Override
        public Double score(RedisKeyNs key, Serializable id, String value) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().score(keyStr,value);
        }

        @Override
        public Long removeRange(RedisKeyNs key, Serializable id, long start, long end) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            long expire = key.getExpire();
            if(expire <= 0){
                return getOperations().removeRange(keyStr,start,end);
            }else{
                Long[] results = new Long[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        results[0] = getOperations().removeRange(keyStr,start,end);
                        getOperations().getOperations().expire(keyStr,expire,TimeUnit.SECONDS);
                        return null;
                    }
                });
                return results[0];
            }
        }

        @Override
        public Long removeRangeByScore(RedisKeyNs key, Serializable id, double min, double max) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            long expire = key.getExpire();
            if(expire <= 0){
                return getOperations().removeRangeByScore(keyStr,min,max);
            }else{
                Long[] results = new Long[1];
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        results[0] = getOperations().removeRangeByScore(keyStr,min,max);
                        getOperations().getOperations().expire(keyStr,expire,TimeUnit.SECONDS);
                        return null;
                    }
                });
                return results[0];
            }
        }

        @Override
        public Set<String> rangeByLex(RedisKeyNs key, Serializable id, ZSetRange range) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().rangeByLex(keyStr,range.getRange());
        }

        @Override
        public Set<String> rangeByLex(RedisKeyNs key, Serializable id, ZSetRange range, ZSetLimit limit) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.ZSET);
            return getOperations().rangeByLex(keyStr,range.getRange(),limit.getLimit());
        }
    }
}
