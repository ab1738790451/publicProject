package com.example.demo.responseResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/4/13 11:38
 * @Version: 1.0.0
 * @Description: 描述
 */
public class ResponseResult implements Serializable {

    private int code;

    private String message;

    private Object data;

    private Map extMap;

    public ResponseResult(int code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(int code,String message){
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public ResponseResult(Object data){
        this.code = 200;
        this.message = "请求成功";
        this.data = data;

    }

    public void putExt(String key,Object value){
        if(extMap == null){
            extMap = new HashMap();
        }
        extMap.put(key,value);
    }

    public Object getExt(String key){
        return extMap == null?null:extMap.get(key);
    }

    public Map getExtMap() {
        return extMap;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
