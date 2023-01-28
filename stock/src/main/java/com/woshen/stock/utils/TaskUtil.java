package com.woshen.stock.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/5/30 15:13
 * @Version: 1.0.0
 * @Description: 任务工具类
 */
public class TaskUtil {
    private static   ExecutorService executorService;
    private static ScheduledExecutorService scheduledExecutorService;


    private TaskUtil(){}

    static {
        //ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,10,0L,TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        executorService = Executors.newFixedThreadPool(10);
         scheduledExecutorService = Executors.newScheduledThreadPool(10);
    }

    /**
     * 执行任务一次
     * @param task
     */
    public static void exec(Runnable task){
        executorService.execute(task);
    }

    /**
     * 延迟固定时间后开始执行任务
     * @param task
     * @param delay
     * @param timeUnit
     */
    public static void scheduledExec(Runnable task, long delay, TimeUnit timeUnit){
        scheduledExecutorService.schedule(task,delay,timeUnit);
    }

    /**
     * 迟延initialDelay时间后开始循环执行任务，并且每隔period时间就会执行下一次任务
     * @param task
     * @param initialDelay
     * @param period
     * @param timeUnit
     */
    public static void scheduleAtFixedRateExec(Runnable task, long initialDelay, long period,TimeUnit timeUnit){
        scheduledExecutorService.scheduleAtFixedRate(task,initialDelay,period,timeUnit);
    }

    /**
     * 延迟initialDelay时间后开始循环执行任务，并且任务执行完成后delay时间才开始执行下一次任务
     * @param task
     * @param initialDelay
     * @param delay
     * @param timeUnit
     */
    public static void scheduleWithFixedDelayExec(Runnable task, long initialDelay, long delay,TimeUnit timeUnit){
        scheduledExecutorService.scheduleWithFixedDelay(task,initialDelay,delay,timeUnit);
    }
}
