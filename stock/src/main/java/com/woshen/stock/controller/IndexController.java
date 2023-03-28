package com.woshen.stock.controller;

import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.base.utils.TreeNodeUtil;
import com.woshen.common.webcommon.model.Bool;
import com.woshen.common.webcommon.model.DefaultUserModel;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.common.webcommon.utils.ThreadWebLocalUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: liuhaibo
 * @Date: 2023/2/10 9:53
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("/")
@Controller
public class IndexController {


    @RequestMapping("index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("/index");
        DefaultUserModel user = ThreadWebLocalUtil.getUser();
        return mav;
    }


   /* @RequestMapping(value = "loadMenu",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult loadMenu(HttpServletRequest request, HttpServletResponse response){

        return new ResponseResult(new TreeNodeUtil(menuServiceImpl.selectAll(menu)).getTreeDatas());
    }*/

}
