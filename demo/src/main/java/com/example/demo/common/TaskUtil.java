package com.example.demo.common;

import java.util.concurrent.*;

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
    private static ExecutorService highConcurrencyExecutorService;

    private TaskUtil(){}

    static {
         executorService = Executors.newFixedThreadPool(10);
         scheduledExecutorService = Executors.newScheduledThreadPool(10);
         highConcurrencyExecutorService = new ThreadPoolExecutor(1,100,0,TimeUnit.NANOSECONDS,new LinkedBlockingQueue<>(),new ThreadFactoryBuilder(),new ThreadPoolExecutor.AbortPolicy());
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

    /**
     * 执行一次任务（并发数较高的情况下使用）
     * @param task
     */
    public static void  HighConcurrencyExec(Runnable task){
        highConcurrencyExecutorService.execute(task);
    }
}
