package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.LocalCahceUtil;
import com.example.demo.common.TreeNodeUtil;
import com.example.demo.entity.TencentIpModel;
import com.example.demo.responseResult.ResponseResult;
import com.example.demo.server.MenuService;
import com.example.demo.server.TestService;
import com.example.demo.utils.ByteUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        return "testFour";
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


    @RequestMapping("toEdit")
    @ResponseBody
    public ModelAndView toEdit(HttpServletRequest request,HttpServletResponse response,Integer lastLayId,Integer id){
         ModelAndView modelAndView = new ModelAndView("/testFour");
         modelAndView.addObject("lastLayId",lastLayId);
         modelAndView.addObject("id",id);
         return  modelAndView;
    }

}
