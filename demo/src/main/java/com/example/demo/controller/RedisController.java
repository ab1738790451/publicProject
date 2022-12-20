package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.redis.constants.DefaultRedisKeyNs;
import com.example.demo.responseResult.ResponseResult;
import com.example.demo.redis.utils.RedisUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/11/30 16:29
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("redis")
@RestController
public class RedisController {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("put")
    public ResponseResult put(String key,String value){
        //redisTemplate.opsForValue().set(key,"[111,222,333]",3, TimeUnit.SECONDS);
        ListOperations listOperations = redisTemplate.opsForList();
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Long size = listOperations.size(key);
                size = size == null?0:size;
                System.err.println("当前数组长度"+size);
                listOperations.rightPush(key, size);
                listOperations.getOperations().expire(key,60,TimeUnit.SECONDS);
                return null;
            }
        });
        //listOperations.getOperations().unlink(key);
        //Object o = listOperations.leftPop(key);
        //System.err.println(o);
        return new ResponseResult("200");
    }

    @RequestMapping("put2")
    public ResponseResult put2(String key,String value){
        //redisTemplate.opsForValue().set(key,"[111,222,333]",3, TimeUnit.SECONDS);

        Map<String,Object> map = new HashMap<>();
        map.put("1","a");
        map.put("2","b");
        Integer append = RedisUtil.stringExecutor().append(DefaultRedisKeyNs.DEFAULT_LOCK_KEY, key, JSONObject.toJSONString(map));
        return new ResponseResult(append);
    }

    @RequestMapping("get")
    public ResponseResult get(String key){
        return new ResponseResult( RedisUtil.stringExecutor().get(DefaultRedisKeyNs.DEFAULT_LOCK_KEY,key));
    }



    @RequestMapping("lock")
    public ResponseResult lock(String key) throws InterruptedException {
        RLock lock = redissonClient.getLock(key);
        boolean b = lock.tryLock(0,10, TimeUnit.SECONDS);
        if(b){
            Thread.sleep(20000);
            //lock.unlock();
            System.err.println(1);
        }
        return new ResponseResult("200");
    }
}
