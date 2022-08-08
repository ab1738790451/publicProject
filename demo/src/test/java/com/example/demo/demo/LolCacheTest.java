package com.example.demo.demo;

import com.example.demo.common.LocalCahceUtil;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/6/15 16:30
 * @Version: 1.0.0
 * @Description: 描述
 */
public class LolCacheTest {
    public static void main(String[] args) throws InterruptedException {
        String key = "test";
        String value = "test";
        LocalCahceUtil.set(key,value,31);
        LocalCahceUtil.hset(key,"1",value,31);
        LocalCahceUtil.sset(key,value,31);
        LocalCahceUtil.lpush(key,value,31);
        Thread.sleep(1*1000);
        System.err.println("string:"+ LocalCahceUtil.get(key));
        System.err.println("hash:"+ LocalCahceUtil.hget(key,"1"));
        System.err.println("set:"+ LocalCahceUtil.contain(key,value));
        System.err.println("list:"+ LocalCahceUtil.lpop(key)+"\n\n\n");
        LocalCahceUtil.deleted(key);
        LocalCahceUtil.hdeleted(key);
        LocalCahceUtil.sdeleted(key);
        LocalCahceUtil.listDeleted(key);
        for (int i = 0; i < 5; i++) {
            System.err.println("string:"+ LocalCahceUtil.get(key));
            System.err.println("hash:"+ LocalCahceUtil.hget(key,"1"));
            System.err.println("set:"+ LocalCahceUtil.contain(key,value));
            System.err.println("list:"+ LocalCahceUtil.lpop(key)+"\n\n\n");
            Thread.sleep(1*1000);
        }

    }
}
