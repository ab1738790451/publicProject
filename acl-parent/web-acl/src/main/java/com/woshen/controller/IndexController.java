package com.woshen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.base.utils.TreeNodeUtil;
import com.woshen.common.beanModel.Bool;
import com.woshen.common.constants.UserType;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.common.webcommon.model.DefaultUserModel;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.common.webcommon.utils.ThreadWebLocalUtil;
import com.woshen.entity.App;
import com.woshen.entity.Menu;
import com.woshen.entity.User;
import com.woshen.service.IAppService;
import com.woshen.service.IMenuService;
import com.woshen.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @Author: liuhaibo
 * @Date: 2023/2/10 9:53
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("/")
@Controller
public class IndexController {

    @Autowired
    private IMenuService menuServiceImpl;

    @Autowired
    private IUserService userServiceImpl;

    @Autowired
    private IAppService appServiceImpl;

    @RequestMapping("index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("/index");
        DefaultUserModel user = ThreadWebLocalUtil.getUser();
        if(user != null){
            String userId = user.getUserId();
            User user1 = userServiceImpl.getById(Integer.valueOf(userId));
            List<App> apps;
            if(UserType.SUPER_ADMIN.equals(user1.getUserType())){
                App app = new App();
                app.setStatus(DataStatus.NORMAL);
                apps = appServiceImpl.selectList(app);
            }else if(StringUtils.isNotBlank(user1.getAppIds())){
                App app = new App();
                app.setStatus(DataStatus.NORMAL);
                QueryWrapper<App> baseWrapper = appServiceImpl.getBaseWrapper(app);
                baseWrapper.in("id",user1.getAppIds().split(","));
                apps =appServiceImpl.list(baseWrapper);
            }else {
                apps = new ArrayList<>();
            }
             mav.addObject("apps",apps);
            }
        return mav;
    }


    @RequestMapping(value = "loadMenu",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult loadMenu(HttpServletRequest request, HttpServletResponse response){
        DefaultUserModel user = ThreadWebLocalUtil.getUser();
        if(user != null){
            String userId = user.getUserId();
            User user1 = userServiceImpl.getById(Integer.valueOf(userId));
            if(user1.getUserType().equals(UserType.SUPER_ADMIN)){
                Menu menu = new Menu();
                menu.setIsMenu(Bool.Y);
                menu.setAppId(-1);
                return new ResponseResult(new TreeNodeUtil(menuServiceImpl.selectAll(menu)).getTreeDatas());
            }else{
                return new ResponseResult(null);
            }
        }
        Menu menu = new Menu();
        menu.setIsMenu(Bool.Y);
        menu.setAppId(-1);
        return new ResponseResult(new TreeNodeUtil(menuServiceImpl.selectAll(menu)).getTreeDatas());
    }

    @RequestMapping("error")
    public ModelAndView error(String errorMsg){
        ModelAndView modelAndView = new ModelAndView("/error");
        modelAndView.addObject("errorMsg",StringUtils.isBlank(errorMsg)?"系统错误！":errorMsg);
        return modelAndView;
    }

    @RequestMapping("error2")
    public String error(){
        return "error2";
    }
}
