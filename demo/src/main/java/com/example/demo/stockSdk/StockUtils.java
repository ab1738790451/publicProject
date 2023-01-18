package com.example.demo.stockSdk;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/15 16:39
 * @Version: 1.0.0
 * @Description: 描述
 */
public class StockUtils {

    private static String STCOK_DOMAIN = "https://api.doctorxiong.club";

    public static Map<String,Object> getStockMessage(String code){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        return restTemplate.getForObject(STCOK_DOMAIN + "/v1/stock?code={code}", Map.class, params);
    }

    public static Map<String,Object> getIndustryRank(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(STCOK_DOMAIN + "/v1/stock/industry/rank", Map.class);
    }


    public static Map<String,Object> getStockRank(String industryCode,Integer pageIndex,Integer pageSize){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> params = new HashMap<>();
        if(industryCode != null && industryCode.trim().length() > 0){
            params.put("industryCode",industryCode);
        }
        params.put("sort","volume");
        params.put("asc",0);
        params.put("pageIndex",pageIndex);
        params.put("pageSize",pageSize);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> httpEntity = new HttpEntity<>(JSONObject.toJSONString(params),headers);
        return restTemplate.postForObject(STCOK_DOMAIN + "/v1/stock/rank",httpEntity ,Map.class);
    }


    public static Map<String,Object> getKlineMin(String code){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        return restTemplate.getForObject(STCOK_DOMAIN + "/v1/stock/min?code={code}",Map.class, params);
    }


    public static Map<String,Object> getKlineDay(String code, LocalDate startDate,LocalDate endDate){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        params.put("startDate",startDate.format(dateTimeFormatter));
        if(endDate == null){
            endDate = LocalDate.now();
        }
        params.put("endDate",endDate.format(dateTimeFormatter));
        return restTemplate.getForObject(STCOK_DOMAIN + "/v1/stock/kline/day?code={code}&startDate={startDate}&endDate={endDate}",Map.class, params);
    }


    public static Map<String,Object> getKlineWeek(String code, LocalDate startDate,LocalDate endDate){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        params.put("startDate",startDate.format(dateTimeFormatter));
        if(endDate == null){
           endDate = LocalDate.now();
        }
        params.put("endDate",endDate.format(dateTimeFormatter));
        return restTemplate.getForObject(STCOK_DOMAIN + "/v1/stock/kline/week?code={code}&startDate={startDate}&endDate={endDate}",Map.class, params);
    }

    public static Map<String,Object> getKlineMonth(String code, LocalDate startDate,LocalDate endDate){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        params.put("startDate",startDate.format(dateTimeFormatter));
        if(endDate == null){
            endDate = LocalDate.now();
        }
        params.put("endDate",endDate.format(dateTimeFormatter));
        return restTemplate.getForObject(STCOK_DOMAIN + "/v1/stock/kline/month?code={code}&startDate={startDate}&endDate={endDate}",Map.class, params);
    }

    public static Map<String,Object> getKlineAll(String keyWord){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> params = new HashMap<>();
        params.put("keyWord",keyWord);
        return restTemplate.getForObject(STCOK_DOMAIN + "/v1/stock/all?keyWord={keyWord}",Map.class, params);
    }

    public static void main(String[] args) {
      /*  Map<String, Object> industryRank = getIndustryRank();
        System.err.println(JSONObject.toJSONString(industryRank));*/

      /*  Map<String, Object> sw_yysw = getStockRank("sw_yysw", 1, 20);
        System.err.println(JSONObject.toJSONString(sw_yysw));*/

        Map<String, Object> stockMessage = getStockMessage("000716");
        System.err.println(JSONObject.toJSONString(stockMessage));
    }
}
