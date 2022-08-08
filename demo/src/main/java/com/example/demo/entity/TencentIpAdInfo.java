package com.example.demo.entity;

import lombok.Data;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/2/10 15:11
 * @Version: 1.0.0
 * @Description: 描述
 */
@Data
public class TencentIpAdInfo {

    /**
     * 国家
     */
   private String nation;

    /**
     * 省
     */
   private String province;

    /**
     * 市
     */
   private String city;

    /**
     * 区
     */
   private String district;

    /**
     * 行政区划代码
     */
   private Integer adcode;
}
