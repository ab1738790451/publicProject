package com.woshen.common.config;

import com.alibaba.fastjson.JSONObject;
import com.woshen.common.webcommon.model.ResponseResult;
import com.woshen.common.webcommon.utils.WebUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;

/**
 * @Author: liuhaibo
 * @Date: 2023/2/17 15:58
 * @Version: 1.0.0
 * @Description: 异常统一处理、字符串参数统一处理
 */
//@ControllerAdvice
public class BaseControllerAdvice {

    @ExceptionHandler(Exception.class)
    public void resultException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(WebUtils.isAjax(request)){
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(JSONObject.toJSONString(new ResponseResult(500,"系统错误！")));
            response.getWriter().flush();
            response.getWriter().close();
        }
        throw new RuntimeException(e);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest webRequest, Locale locale) throws IllegalAccessException {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        Object target = binder.getTarget();
        if(target != null){
            Field[] declaredFields = target.getClass().getDeclaredFields();
            for (Field f:declaredFields
            ) {
                if(f.getType().getTypeName().equals(String.class.getTypeName())){
                    f.setAccessible(true);
                    if("".equals(f.get(target))){
                        f.set(target,null);
                    }
                    f.setAccessible(false);
                }
            }
        }
    }
}
