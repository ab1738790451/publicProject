package com.woshen.common.baseTempl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.webcommon.model.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Map;

/**
 * @Author: liuhaibo
 * @Date: 2022/8/15 14:23
 * @Version: 1.0.0
 * @Description: 描述
 */
public abstract class AbstractController<PK extends Serializable,T extends BaseEntity<PK>> {

    String module;

    public AbstractController(){
        RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class);
        if (rm == null) {
            throw new RuntimeException("Not Found RequestMapping annotation in class " + this.getClass().getName());
        } else {
            String name = rm.name();
            if(name != null && name.trim().length() > 0){
                this.module = name;
            }else{
                String[] modules = rm.value();
                if (modules != null && modules.length > 0 && (modules[0] != null && modules[0].trim().length() != 0)) {
                    this.module = modules[0];
                } else {
                    throw new RuntimeException("module path not defined for RequestMapping annotation in class " + this.getClass().getName());
                }
            }
        }
    }

    protected String getModule() {
        return module;
    }


    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response,T queryData){
        ModelAndView mav = new ModelAndView(getModule() + "/list");
        Page<T> pageData = this.loadList(queryData);
        mav.addObject("pageData",pageData);
        mav.addObject("queryData",queryData);
        afterList(request,response,mav,queryData,pageData);
        return mav;
    }

    public void afterList(HttpServletRequest request, HttpServletResponse response,ModelAndView mav,T queryData,Page<T> pageData){

    }

    @RequestMapping("toEdit")
    public ModelAndView toedit(HttpServletRequest request, HttpServletResponse response,String lastLayId,PK pk){
        ModelAndView modelAndView = new ModelAndView(getModule() + "/edit");
        modelAndView.addObject("lastLayId",lastLayId);
        T data = null;
        if(pk != null) {
            data = getService().getById(pk);
        }
        modelAndView.addObject("data", data);
        modelAndView.addAllObjects(afterEdit(request,response,data));
        return  modelAndView;
    }

    public Map<String,?> afterEdit(HttpServletRequest request, HttpServletResponse response, T data){
        return null;
    }

    @RequestMapping("del")
    @ResponseBody
    public ResponseResult del(PK... pks){
      getService().del(pks);
      return new ResponseResult(200,"SUCCESS");
    }

    @RequestMapping("dosave")
    @ResponseBody
    public ResponseResult dosave(@RequestBody T queryData){
        Integer pk = getService().dosave(queryData);
        int i = pk == null ? 500 : 200;
        return new ResponseResult(i,i==200?"SUCCESS":"ERROR");
    }
    public Page<T> loadList(T queryData){
        Integer pageSize = queryData.getPageInfo().getPageSize();
        if(pageSize > 100){
            queryData.getPageInfo().setPageSize(10);
        }
        return getService().selectPage(queryData);
    }

   public abstract BaseService<PK,T> getService();
}
