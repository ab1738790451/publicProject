package com.example.demo.redis.utils;

import com.example.demo.config.SpringUtils;
import com.example.demo.redis.core.*;
import com.example.demo.redis.constants.RedisKeyType;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
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

    private RedisUtil(){}

    public static StringExecutor stringExecutor(){
        return stringExecutor;
    }

    public static SetExecutor SetExecutor(){
        return setExecutor;
    }

    public static ListExecutor listExecutor(){
        return listExecutor;
    }

    public static HashExecutor hashExecutor(){
        return hashExecutor;
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

    public static  long getExpire(RedisKeyNs key,Serializable id){
        String keyStr = getKeyStr(key,id,null);
        return redisTemplate.getExpire(keyStr);
    }


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
            return redisTemplate.opsForSet().remove(keyStr,values);
        }

        @Override
        public String pop(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.SET);
            return redisTemplate.opsForSet().pop(keyStr);
        }

        @Override
        public Set<String> pop(RedisKeyNs key, Serializable id, long count) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.SET);
            return new HashSet<>(redisTemplate.opsForSet().pop(keyStr,count));
        }

        @Override
        public  Boolean move(RedisKeyNs key, Serializable id, String value, RedisKeyNs destKey, Serializable destId) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.SET);
            String destKeyStr =  getKeyStr(destKey,destId,RedisKeyType.SET);
            return redisTemplate.opsForSet().move(keyStr,value,destKeyStr);
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
            return  redisTemplate.opsForList().remove(keyStr, count,value);
        }

        @Override
        public String index(RedisKeyNs key, Serializable id, long index) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            return redisTemplate.opsForList().index(keyStr,index);
        }


        @Override
        public String lPop(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            return redisTemplate.opsForList().leftPop(keyStr);
        }

        @Override
        public String rPop(RedisKeyNs key, Serializable id) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.LIST);
            return redisTemplate.opsForList().rightPop(keyStr);
        }
    }

    public static class HashExecutor implements RedisHashOperations{

        private  HashOperations<String, String, String> getOperations(){
            return redisTemplate.opsForHash();
        }

        @Override
        public Long delete(RedisKeyNs key, Serializable id, String... hashKeys) {
            String keyStr =  getKeyStr(key,id,RedisKeyType.HASH);
            return getOperations().delete(keyStr,hashKeys);
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
}
