package com.example.demo.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/4/23 17:05
 * @Version: 1.0.0
 * @Description: 描述
 */
public class TestExecutors {

    public static String TEST = "AA";
    public static ExecutorService executorService =null;
    public static ExecutorService getExectors(){
        executorService = Executors.newFixedThreadPool(4);
        return executorService;
    }
}
