package com.example.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.DemoTestListener;
import com.example.demo.entity.IpTest;
import com.example.demo.responseResult.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/8/4 14:34
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("ip")
@RestController
public class Ip2regionController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("parsingIp")
    public ResponseResult parsingIp(String ip) throws Exception {
        byte[] bytes = Searcher.loadContentFromFile("D:\\vsProjects\\ip2region\\data\\xdb-master\\ip2region.xdb");
        Searcher searcher = Searcher.newWithBuffer(bytes);
        //Searcher searcher = Searcher.newWithFileOnly("D:\\vsProjects\\ip2region\\data\\ip2region.xdb");
        String search = searcher.search(ip);
        String[] split = search.split("\\|");
        return new ResponseResult(Arrays.toString(split));
    }

    @RequestMapping("test")
    public ResponseResult test() throws IOException {
        File file = new File("C:\\Users\\Administrator\\Desktop\\ip.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        List<String> ipAddresses = new ArrayList<>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            String ip = line.replace("\"", "");
            if(StringUtils.isBlank(ip)){
                continue;
            }
            System.err.println(ip);
            String result = restTemplate.getForObject("http://49.232.13.134:19500/api/ip/test?ip=" + ip, String.class);
            Map map = JSONObject.parseObject(result, Map.class);
            String model = (String) map.get("model");
            System.err.println(model);
            ipAddresses.add(model);
        }
        bufferedReader.close();
        return new ResponseResult(ipAddresses);
    }


    @RequestMapping("test2")
    public ResponseResult test2() throws IOException {
        ExcelReaderBuilder read = EasyExcel.read("D:\\Documents\\WXWork\\1688850284206409\\Cache\\File\\2022-08\\IP.xlsx", IpTest.class,new DemoTestListener());
        read.doReadAll();
        return new ResponseResult(null);
    }
}
