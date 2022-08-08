package com.example.demo.common;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.IpTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/8/5 15:03
 * @Version: 1.0.0
 * @Description: 描述
 */
public class DemoTestListener extends AnalysisEventListener<IpTest> {

    private static final int BATCH_COUNT = 100;

    private static  int INDEX = 1;

    List<IpTest> list = new ArrayList<>();

    List<IpTest> list2 = new ArrayList<>();

    private  static  RestTemplate restTemplate = new RestTemplate();

    @Override
    public void invoke(IpTest ipTest, AnalysisContext analysisContext) {
              list.add(ipTest);
              if(list.size() >= BATCH_COUNT){
                     handleData();
                     list.clear();
                     INDEX ++ ;
              }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                handleData();
                writeExcel();
    }


    private void handleData(){
        List<String> ips = list.stream().map(t -> t.getUserIp().trim()).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(ips)){
            return;
        }
        Map<String,Object> params = new HashMap<>();
        params.put("ips",ips.stream().toArray(String[]::new));
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost/api/ip/test2?ips={ips}", String.class,params);
        String body = responseEntity.getBody();
        Map map = JSONObject.parseObject(body, Map.class);
        JSONArray model = (JSONArray)map.get("model");
        List<String> results = model.toJavaList(String.class);
        for (int i = 0;i< list.size();i++
             ) {
               list.get(i).setIpAddress(results.get(i));
               list2.add(list.get(i));
        }


    }

    private void writeExcel(){
        try {
            File file = new File("D:\\Documents\\WXWork\\1688850284206409\\Cache\\File\\2022-08\\IP2.xlsx");
            if(!file.exists()){
                file.createNewFile();
            }
            ExcelWriterBuilder write = EasyExcelFactory.write(file);
            ExcelWriter excelWriter = write.build();
            WriteSheet writeSheet = new WriteSheet();
            writeSheet.setSheetNo(0);
            writeSheet.setSheetName("test");
            List<List<String>> head = new ArrayList<>();
            List<String> head0 = new ArrayList<>();
            head0.add("ip");
            head0.add("ip");
            head.add(head0);

            List<String> head1 = new ArrayList<>();
            head0.add("原地址信息");
            head0.add("原地址信息");
            head.add(head1);

            List<String> head2 = new ArrayList<>();
            head0.add("新地址信息");
            head0.add("新地址信息");
            excelWriter.write(list2, writeSheet);
            excelWriter.finish();
            excelWriter.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
