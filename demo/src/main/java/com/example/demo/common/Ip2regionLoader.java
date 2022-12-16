package com.example.demo.common;

import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/8/8 16:43
 * @Version: 1.0.0
 * @Description: 描述
 */
@Service
public class Ip2regionLoader implements ApplicationRunner {

    private static Searcher searcher;

    private static final String dbPath = "/static/other/ip2region.xdb";

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource(dbPath);
        InputStream in = classPathResource.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = new byte[8192];
        int len = 0;
        while ( (len = in.read(bytes)) != -1){
              out.write(bytes,0,len);
        }
        out.flush();
        byte[] bytes1 = out.toByteArray();
        searcher = Searcher.newWithBuffer(bytes1);
        out.close();
        in.close();

    }

    public static  Searcher getInstance(){
        return searcher;
    }
}
