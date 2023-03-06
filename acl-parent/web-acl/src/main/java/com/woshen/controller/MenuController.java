package com.woshen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.base.utils.TreeNodeUtil;
import com.woshen.common.baseTempl.AbstractController;
import com.woshen.common.baseTempl.BaseService;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.entity.Menu;
import com.woshen.service.IMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: liuhaibo
 * @Date: 2023/2/12 11:25
 * @Version: 1.0.0
 * @Description: 描述
 */
@RequestMapping("menu")
@Controller
public class MenuController extends AbstractController<Integer,Menu> {

    @Resource
    private IMenuService menuServiceImpl;

    @Override
    public BaseService getService() {
        return menuServiceImpl;
    }

    @Override
    public Page<Menu> loadList(Menu queryData) {
        queryData.setStatus(DataStatus.NORMAL);
        Integer parentId = queryData.getParentId();
        if(parentId == null){
            queryData.setParentId(-1);
        }
        Page<Menu> menuPage = super.loadList(queryData);
        if(parentId == null){
            queryData.setParentId(null);
        }
        return menuPage;
    }


    @RequestMapping("treeList")
    @ResponseBody
    public ResponseResult treeList(HttpServletRequest request, HttpServletResponse response,Menu queryData){
        Integer parentId = queryData.getParentId();
        if(parentId == null){
            queryData.setParentId(-1);
        }
        queryData.setStatus(DataStatus.NORMAL);
        Page<Menu> menuPage = super.loadList(queryData);
        List<Menu> records = menuPage.getRecords();
        if(!CollectionUtils.isEmpty(records)){
            List<Integer> ids = records.stream().map(Menu::getId).collect(Collectors.toList());
            iteratedSubitem(records,ids);
        }
        return new ResponseResult(new TreeNodeUtil(records));
    }
    /**
     * 迭代子项
     * @param menus
     * @param parentIds
     */
    private void iteratedSubitem(List<Menu> menus,List<Integer> parentIds){
        Menu menu = new Menu();
        menu.setStatus(DataStatus.NORMAL);
        QueryWrapper<Menu> baseWrapper = menuServiceImpl.getBaseWrapper(menu);
        baseWrapper.in("parent_id",parentIds);
        List<Menu> list = menuServiceImpl.list(baseWrapper);
        if(!CollectionUtils.isEmpty(list)){
            menus.addAll(list);
            iteratedSubitem(menus,list.stream().map(Menu::getId).collect(Collectors.toList()));
        }
    }

    @Override
    public ModelAndView toedit(HttpServletRequest request, HttpServletResponse response, String lastLayId, Integer pk) {
        ModelAndView mav = super.toedit(request, response, lastLayId, pk);
        Object data = mav.getModel().get("data");
        if(data ==null){
            Menu menu = new Menu();
            String parentId = request.getParameter("parentId");
            if(StringUtils.isNotBlank(parentId)) {
                menu.setParentId(Integer.valueOf(parentId));
            }
            String appId = request.getParameter("appId");
            if(StringUtils.isNotBlank(appId)) {
                menu.setAppId(Integer.valueOf(appId));
            }
                mav.addObject("data",menu);
        }

        return mav;
    }
}
