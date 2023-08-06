package com.woshen.stock.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/15 16:39
 * @Version: 1.0.0
 * @Description: 后台地址 https://doctorxiong.club/api/#api-Token-token%E4%BD%BF%E7%94%A8%E7%A4%BA%E4%BE%8B
 */
public class StockUtils {

    private static String STCOK_DOMAIN = "https://api.doctorxiong.club";

    /**
     * 获取股票基本信息
     * @param code
     * @return
     */
    public static Map<String,Object> getStockMessage(String code){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        return restTemplate.getForObject(STCOK_DOMAIN + "/v1/stock?code={code}", Map.class, params);
    }

    /**
     * 获取行业板块
     * @return
     */
    public static Map<String,Object> getIndustryRank(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(STCOK_DOMAIN + "/v1/stock/industry/rank", Map.class);
    }

    /**
     * 获取股票排行
     * @param industryCode
     * @param pageIndex
     * @param pageSize
     * @return
     */
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

    /**
     * 获取分时数据
     * @param code
     * @return
     */
    public static Map<String,Object> getKlineMin(String code){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        return restTemplate.getForObject(STCOK_DOMAIN + "/v1/stock/min?code={code}",Map.class, params);
    }

    /**获取日数据
     *
     * @param code
     * @param startDate
     * @param endDate
     * @return
     */
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

    /**
     * 获取周数据
     * @param code
     * @param startDate
     * @param endDate
     * @return
     */
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

    /**
     * 获取月数据
     * @param code
     * @param startDate
     * @param endDate
     * @return
     */
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

    /**
     *获取全部股票
     * @param keyWord
     * @return
     */
    public static Map<String,Object> getKlineAll(String keyWord){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> params = new HashMap<>();
        params.put("keyWord",keyWord);
        return restTemplate.getForObject(STCOK_DOMAIN + "/v1/stock/all?keyWord={keyWord}",Map.class, params);
    }

    /**
     * 判断股票是否退市
     * @param code
     * @return
     */
    public static boolean isTuishi(String code){
        Map<String, Object> stockMessage = getStockMessage("002417");
        List<Map> data = (List<Map>)stockMessage.get("data");
        String name = (String) data.get(0).get("name");
        if(name.endsWith("退")){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
      /*  Map<String, Object> industryRank = getIndustryRank();
        System.err.println(JSONObject.toJSONString(industryRank));*/

      /*  Map<String, Object> sw_yysw = getStockRank("sw_yysw", 1, 20);
        System.err.println(JSONObject.toJSONString(sw_yysw));*/

        //Map<String, Object> stockMessage = getStockMessage("002417");
        System.err.println(isTuishi("002417"));
    }
}
