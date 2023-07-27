package com.woshen.acl.utils;

import com.woshen.common.base.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @Author: liuhaibo
 * @Date: 2023/7/27 10:30
 * @Version: 1.0.0
 * @Description: 描述
 */
public class LogUtils {

    private final static Logger LOG = LoggerFactory.getLogger(LogUtils.class);

    public static void debug(String msg,Object... values){
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        Logger logger = LoggerFactory.getLogger(className);
        if(logger.isDebugEnabled() && StringUtils.isNotBlank(msg)){
            try {
                String intactMsg = formatMsg(msg,values);
                logger.debug(intactMsg);
            }catch (Exception e){
                LOG.error("msg:"+msg+",values:"+ Arrays.toString(values),e);
            }
        }
    }


    public static void trace(String msg,Object... values){
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        Logger logger = LoggerFactory.getLogger(className);
        if(logger.isTraceEnabled() && StringUtils.isNotBlank(msg)){
            try {
                String intactMsg = formatMsg(msg,values);
                logger.trace(intactMsg);
            }catch (Exception e){
                LOG.error("msg:"+msg+",values:"+ Arrays.toString(values),e);
            }
        }
    }


    public static void info(String msg,Object... values){
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        Logger logger = LoggerFactory.getLogger(className);
        if(logger.isInfoEnabled() && StringUtils.isNotBlank(msg)){
            try {
                String intactMsg = formatMsg(msg,values);
                logger.info(intactMsg);
            }catch (Exception e){
                LOG.error("msg:"+msg+",values:"+ Arrays.toString(values),e);
            }
        }
    }

    public static void warn(String msg,Object... values){
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        Logger logger = LoggerFactory.getLogger(className);
        if(logger.isWarnEnabled() && StringUtils.isNotBlank(msg)){
            try {
                String intactMsg = formatMsg(msg,values);
                logger.warn(intactMsg);
            }catch (Exception e){
                LOG.error("msg:"+msg+",values:"+ Arrays.toString(values),e);
            }
        }
    }

    public static void warn(Throwable throwable,String msg,Object... values){
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        Logger logger = LoggerFactory.getLogger(className);
        if(logger.isWarnEnabled() && StringUtils.isNotBlank(msg)){
            try {
                String intactMsg = formatMsg(msg,values);
                logger.warn(intactMsg,throwable);
            }catch (Exception e){
                LOG.error("msg:"+msg+",values:"+ Arrays.toString(values),e);
            }
        }
    }

    public static void error(Throwable throwable,String msg,Object... values){
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        Logger logger = LoggerFactory.getLogger(className);
        if(logger.isErrorEnabled() && StringUtils.isNotBlank(msg)){
            try {
                String intactMsg = formatMsg(msg,values);
                logger.error(intactMsg,throwable);
            }catch (Exception e){
                LOG.error("msg:"+msg+",values:"+ Arrays.toString(values),e);
            }
        }
    }

    private static String formatMsg(String msg,Object... values){
        if(StringUtils.isBlank(msg) || msg.indexOf("%") == -1 || values.length == 0){
            return msg;
        }
        return String.format(msg,values);
    }
}
