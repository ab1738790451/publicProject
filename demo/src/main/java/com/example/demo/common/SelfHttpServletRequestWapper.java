package com.example.demo.common;



import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/4/21 9:16
 * @Version: 1.0.0
 * @Description: 描述
 */
public class SelfHttpServletRequestWapper extends HttpServletRequestWrapper {

     private final byte[] body;

    public SelfHttpServletRequestWapper(HttpServletRequest request) {
        super(request);
        String bodyString = getBodyString(request);
        body = bodyString.getBytes(Charset.forName("UTF-8"));
    }

    public String  getBodyString(HttpServletRequest request){
        String bodyString ="";
        String contentType = request.getContentType();
        if(StringUtils.isNotBlank(contentType)&&(contentType.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE) || contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE))){
            Enumeration<String> parameterNames = request.getParameterNames();
            while(parameterNames.hasMoreElements()){
                String s = parameterNames.nextElement();
                bodyString += s+"="+request.getParameter(s)+"&";
            }
            bodyString = bodyString.endsWith("&")?bodyString.substring(0,bodyString.length()-1):bodyString;
            return bodyString;
        }
         try{
             byte[] bytes = StreamUtils.copyToByteArray(request.getInputStream());
             bodyString = new String(bytes,"UTF-8");
         }catch (Exception e){
             e.printStackTrace();
         }
       return bodyString;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
      final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }
}
