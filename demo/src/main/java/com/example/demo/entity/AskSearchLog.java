package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 页面访问记录表
 * </p>
 *
 * @author luchen
 * @since 2021-06-01
 */
@Data
@Accessors(chain = true)
public class AskSearchLog {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 站点ID
     */
    private Integer siteId;

    /**
     * 用户base_uid
     */
    private Integer baseUid;

    /**
     * 用户uid
     */
    private Integer uid;

    /**
     * 访问url
     */
    private String url;

    /**
     * 搜索关键字
     */
    private String searchKeywords;

    /**
     * 搜索结果条数
     */
    private Integer searchResult;

    /**
     * 匿名id
     */
    private String anonymousId;

    /**
     * ip地址
     */
    private String clientIp;

    /**
     * 来源类型
     */
    private String fromType;

    /**
     * 来源客户端名称
     */
    private String fromClient;

    /**
     * user_agent
     */
    private String userAgent;

    /**
     * 创建时间
     */
    private Date createTime;


}
