package com.example.demo.entity;

import lombok.Data;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/2/10 15:16
 * @Version: 1.0.0
 * @Description: 描述
 */
@Data
public class TencentIpResult {

    private String ip;

    private TencenIpLocation location;

    private TencentIpAdInfo ad_info;

}
