package com.example.demo.common;

import java.util.concurrent.ThreadFactory;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/10/14 15:17
 * @Version: 1.0.0
 * @Description: 描述
 */
public class ThreadFactoryBuilder  implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("superExecutorsThread");
        return thread;
    }
}
