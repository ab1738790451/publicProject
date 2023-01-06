package com.example.test;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2023/1/4 13:54
 * @Version: 1.0.0
 * @Description: 描述
 */
@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("testOne")
    public String test(String key) throws InterruptedException {
        RLock lock = redissonClient.getLock(key);
        boolean b = lock.tryLock(0,10, TimeUnit.SECONDS);
        if(b){
            Thread.sleep(20000);
            //lock.unlock();
            System.err.println(1);
        }
        return "";
    }

    @RequestMapping("testTwo")
    public String testTwo(String key,String value) throws InterruptedException {
        redisTemplate.opsForValue().set(key,value,60,TimeUnit.SECONDS);
        return "";
    }
}
