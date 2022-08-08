package com.example.demo.entity;

import lombok.Data;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/2/10 15:08
 * @Version: 1.0.0
 * @Description: 描述
 */
@Data
public class TencentIpModel {

    private Integer status;

    private String message;

    private TencentIpResult result;

}
