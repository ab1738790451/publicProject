package com.example.demo.annotation.handler;

import com.example.demo.annotation.annotations.FieldSetVal;
import com.example.demo.entity.Test;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/4/26 11:41
 * @Version: 1.0.0
 * @Description: 描述
 */
@Aspect
@Component
public class FieldSetValHandler {


    @Pointcut("@annotation(com.example.demo.annotation.annotations.FieldSetVal)")
    public void pointcut(){

    }

    @Before("pointcut()")
    public void before(JoinPoint point) throws IllegalAccessException {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Object target = point.getTarget();
        Class<?> aClass = target.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field f:
             fields) {
           if(f.isAnnotationPresent(FieldSetVal.class)){
               FieldSetVal annotation = f.getAnnotation(FieldSetVal.class);
               String val = annotation.val();
               f.setAccessible(true);
               f.set(target,val);
           }
        }
        System.err.println("before执行了");
    }

//    @Around("pointcut()")
//    public void around(ProceedingJoinPoint point) throws Throwable {
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        Method method = signature.getMethod();
//        Object target = point.getTarget();
//        Class<?> aClass = target.getClass();
//                FieldSetVal annotation = method.getAnnotation(FieldSetVal.class);
//                String val = annotation.val();
//        Test target1 = (Test) target;
//        target1.setName(val);
//        System.err.println("around执行了");
//        point.proceed();
//        System.err.println("around执行了");
//        return;
//    }

    @After("pointcut()")
    public void after(){
        System.err.println("after执行了");
    }


    @AfterReturning("pointcut()")
    public void AfterReturning(){
        System.err.println("AfterReturning执行了");
    }


    @AfterThrowing("pointcut()")
    public void AfterThrowing(){
        System.err.println("AfterThrowing执行了");
    }
}
