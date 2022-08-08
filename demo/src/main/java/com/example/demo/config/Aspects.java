package com.example.demo.config;

import com.example.demo.common.PublicThreadLocal;
import org.apache.shardingsphere.api.hint.HintManager;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/7/26 11:07
 * @Version: 1.0.0
 * @Description: 描述
 */
@Aspect
@Component
public class Aspects {

     @Pointcut("execution(* com.example.demo.mapper.testOne.TbMapper.*(..))")
    public void pointcutOne(){

    }

    @Pointcut("execution(* com.example.demo.mapper.testTwo.TbtMapper.*(..))")
    public void pointcutTwo(){

    }

    @Pointcut("execution(* com.example.demo.mapper.testOne.MenuMapper.*(..))")
    public void pointcutThree(){

    }

    @Before("pointcutOne()")
    public void beforeOne(){
        HintManager.clear();
        HintManager instance = HintManager.getInstance();
        Integer siteId = (Integer)PublicThreadLocal.get("siteId");
        instance.addDatabaseShardingValue("","testone");
        instance.addTableShardingValue("tb",siteId);
    }

    @Before("pointcutTwo()")
    public void beforeTwo(){
        HintManager.clear();
        HintManager instance = HintManager.getInstance();
        Integer siteId = (Integer)PublicThreadLocal.get("siteId");
        instance.addDatabaseShardingValue("","testwo");
        instance.addTableShardingValue("tbt",siteId);
    }

    @Before("pointcutThree()")
    public void beforeThree(){
        HintManager.clear();
        HintManager instance = HintManager.getInstance();
        //Integer siteId = (Integer)PublicThreadLocal.get("siteId");
        instance.addDatabaseShardingValue("","testone");
        //instance.addTableShardingValue("tbt",siteId);
    }
}
