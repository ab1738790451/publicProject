package com.example.demo.demo;


import com.example.demo.entity.Test;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/4/23 8:59
 * @Version: 1.0.0
 * @Description: 描述
 */
public class MD5Test {
      private static int a = 0;
    public static void main(String[] args) throws Exception{

       String a=null;

       String b=null;

       String c= null;

       String f = a+b+c+"ssd";
       System.err.println(f);
    }


    public static void getFormatTime(){
        String s = "2021-12-01";
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parse = LocalDate.parse(s, format);
        LocalDateTime localDateTime = parse.atStartOfDay();
       System.err.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }


    public  void md5Test() throws UnsupportedEncodingException {
        String a1=null;
        Integer i = null;
        StringBuilder append = new StringBuilder().append(i).append(a1);
        System.err.println(append);
        String name = null;
        if(name == null || name.length()<1){
            System.err.println("name：不要空");
        }
        String name2 = "谢谢你马";
        new StringBuffer(name);
        String s = new StringBuilder(name).append(name2).toString();
        System.err.println("原值："+s);
        String sMd5 = DigestUtils.md5Hex(s);
        System.err.println("第一次加密："+sMd5);

        System.err.println("第二次加密："+DigestUtils.md5Hex(new StringBuilder(name).append(name2).toString()));
        //System.err.println("第二次加密："+sMd5);
        String s1 = org.springframework.util.DigestUtils.md5DigestAsHex(new StringBuilder(name).append(name2).toString().getBytes("UTF-8"));
        System.err.println("spring加密："+s1);

        if(sMd5.equals(s1)){
            System.err.println("密钥一致");
        }else{
            System.err.println("密钥不一致");
        }
    }

    private static synchronized void forE(){
        if(a < 50){
            a++;
        }
        System.err.println(a);
    }
}
