package com.woshen.stock.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/6/21 10:47
 * @Version: 1.0.0
 * @Description: 描述
 */
@Data
@TableName("menu")
public class Menu  {

    private Integer id;

    private String title;

    private String url;

    private Integer parentId;

    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
