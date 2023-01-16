package com.example.demo.controller;


import com.example.demo.utils.IpUtil;
import com.example.demo.utils.TreeNodeUtil;
import com.example.demo.entity.TencentIpModel;
import com.example.demo.responseResult.ResponseResult;
import com.example.demo.server.MenuService;
import com.example.demo.server.TestService;
import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.MessageEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/4/13 11:35
 * @Version: 1.0.0
 * @Description: 描述
 */
@Controller
@Api(value = "测试controller",tags = "测试controller")
@RequestMapping("test")
public class TestController {

    @Value("${tencent.api.webService.key:EKNBZ-T6NEG-FA5QI-IJQ3K-AS6N2-JZBDL,EKNBZ-T6NEG-FA5QI-IJQ3K-AS6N2-JZBDK}")
    private List<String> keys;

    @Autowired
   private TestService testService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MenuService menuServiceImpl;

    @ApiOperation(value = "方法一",tags = "方法一",httpMethod = "GET")
    @RequestMapping(value = "/one",method = RequestMethod.GET)
    public String test(HttpServletRequest request, HttpServletResponse response){
        Enumeration<String> headerNames = request.getHeaderNames();
        String ipAddr = IpUtil.getIpAddr(request);
        System.err.println(ipAddr);
        return "test";
    }

    @ApiOperation(value = "方法二",tags = "方法二",httpMethod = "POST")
    @RequestMapping(value = "/two")
    @ResponseBody
    public ResponseResult test2(String name,String userName,String message){
        TestWebSocket.sendMessageAll(name,userName,message);
        return new ResponseResult(200,"查询成功",testService.queryAll());
    }

    @ApiOperation(value = "方法三",tags = "方法三",httpMethod = "GET")
    @RequestMapping(value = "/three1",method = RequestMethod.GET)
    public String test3(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("id",request.getParameter("id"));
        return "getui";
    }

    @ApiOperation(value = "方法三",tags = "方法三",httpMethod = "GET")
    @RequestMapping(value = "/three",method = RequestMethod.GET)
    public ModelAndView test31(HttpServletRequest request,HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("testThree");
        modelAndView.addObject("menus",menuServiceImpl.selectAll());
        modelAndView.addObject("id",request.getParameter("id"));
        return modelAndView;
    }

    @ApiOperation(value = "方法四",tags = "方法四",httpMethod = "GET")
    @RequestMapping(value = "four",method = RequestMethod.GET)
    public ModelAndView test4(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/test");
       /* File resourceAsFile = Resources.getResourceAsFile("d/ff");
        Resources.getResourceAsStream("555");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(new FileInputStream(resourceAsFile));*/
        return mav;
    }

    @ApiOperation(value = "方法五",tags = "方法五",httpMethod = "GET")
    @RequestMapping(value = "five",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getRequest(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        LinkedMultiValueMap<String,Object> linkedMultiValueMap = new LinkedMultiValueMap<>();
        String key = keys.get(0);
        linkedMultiValueMap.add("key",key);
        Map<String,Object> map = new HashMap<>();
        map.put("key",key);
        HttpEntity httpEntity = new HttpEntity(linkedMultiValueMap,new HttpHeaders());
        //String url = "http://localhost:8080/test/four";
        String localUrl = "https://apis.map.qq.com/ws/location/v1/ip?key={key}";
        ResponseEntity<TencentIpModel> forEntity = restTemplate.getForEntity(localUrl, TencentIpModel.class, map);
        TencentIpModel body1 = forEntity.getBody();
       // ResponseEntity<String> exchange = restTemplate.exchange(localUrl, HttpMethod.GET, null, String.class,map);
        //String body = exchange.getBody();
        return new ResponseResult(200,"查询成功",body1);
    }

    @ApiOperation(value = "方法六",tags = "方法六",httpMethod = "GET")
    @RequestMapping(value = "six",method = RequestMethod.GET)
    public String six(HttpServletRequest request,HttpServletResponse response){
        return "testTwo";
    }

    @ApiOperation(value = "加载菜单",tags = "加载菜单",httpMethod = "GET")
    @RequestMapping(value = "loadMenu",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult loadMenu(HttpServletRequest request,HttpServletResponse response){
        return new ResponseResult(new TreeNodeUtil(menuServiceImpl.selectAll()).getTreeDatas());
    }

    @ApiOperation(value = "保存提交",tags = "保存提交",httpMethod = "POST")
    @PostMapping("dosave")
    @ResponseBody
    public ResponseResult dosave(HttpServletRequest request,HttpServletResponse response){
        return new ResponseResult(200,"成功");
    }


    @RequestMapping("toEdit")
    public ModelAndView toEdit(HttpServletRequest request,HttpServletResponse response,Integer lastLayId,Integer id){
         ModelAndView modelAndView = new ModelAndView("/testFour");
         modelAndView.addObject("lastLayId",lastLayId);
         modelAndView.addObject("id",id);
         return  modelAndView;
    }


    @RequestMapping("addMenu")
    @ResponseBody
    public ResponseResult addMenu(){
        menuServiceImpl.insert();
        return  new ResponseResult(200,"SUCCESS");
    }



    @RequestMapping("sse")
    @ResponseBody
    public ResponseResult sse() throws URISyntaxException {
      EventHandler eventHandler =   new EventHandler() {
            @Override
            public void onOpen() throws Exception {
                System.err.println("连接成功");
            }

            @Override
            public void onClosed() throws Exception {

            }

            @Override
            public void onMessage(String s, MessageEvent messageEvent) throws Exception {
                System.err.println("s:"+s);
                System.err.println("data:"+messageEvent.getData());
            }

            @Override
            public void onComment(String s) throws Exception {

            }

            @Override
            public void onError(Throwable throwable) {
               System.err.println("error:"+throwable.getMessage());
            }
        };
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("96.push2.eastmoney.com")
                .addEncodedPathSegments("api/qt/stock/details/sse")
                .addEncodedQueryParameter("fields1", "f1,f2,f3,f4")
                .addEncodedQueryParameter("fields2","f51,f52,f53,f54,f55")
                 .addEncodedQueryParameter("mpi","1000")
                .addEncodedQueryParameter("ut","f057cbcbce2a86e2866ab8877db1d059")
                .addEncodedQueryParameter("fltt","2")
                .addEncodedQueryParameter("pos","-15")
                .addEncodedQueryParameter("secid","0.002195")
                .addEncodedQueryParameter("wbp2u","")
                .addEncodedQueryParameter("wbp2u","|0|0|0|wap")
                .build();
        EventSource eventSource = new EventSource.Builder(eventHandler, url).build();
        eventSource.start();
        return  new ResponseResult(200,"SUCCESS");
    }
}
