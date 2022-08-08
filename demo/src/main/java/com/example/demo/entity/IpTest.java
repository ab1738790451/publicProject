package com.example.demo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/8/5 14:45
 * @Version: 1.0.0
 * @Description: 描述
 */
@Data
public class IpTest {

    @ExcelProperty("user_ip")
    private String userIp;

    @ExcelProperty("user_ip_district")
    private String userIpDistrict;

    @ExcelProperty("解析结果")
    private String ipAddress;
}
