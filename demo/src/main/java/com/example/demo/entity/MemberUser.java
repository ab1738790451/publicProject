package com.example.demo.entity;


import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 会员用户表
 * </p>
 *
 * @author 
 * @since 2021-05-19
 */
@Data

public class MemberUser  {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * uid
     */
    private Integer uid;

    /**
     * 到期时间
     */
    private Date endTime;

    /**
     * 数据创建时间
     */
    private Date createTime;

    /**
     * 最近一次更新时间
     */
    private Date lastupTime;

    /**
     * 数据状态。正常、已过期
     */
    private MemberState status;

    /**
     * 剩余查看答案次数
     */
    private Integer askCount;

    /**
     * 剩余拍照搜索次数
     */
    private Integer photoCount;

    /**
     * 剩余语音搜索次数
     */
    private Integer voiceCount;

    /**
     * 已使用查看答案次数
     */
    private Integer usedAskCount;

    /**
     * 已使用拍照搜索次数
     */
    private Integer usedPhotoCount;

    /**
     * 已使用语音搜索次数
     */
    private Integer usedVoiceCount;

    /**
     * 已过期查看答案次数
     */
    private Integer expiredAskCount;

    /**
     * 已过期拍照搜索次数
     */
    private Integer expiredPhotoCount;

    /**
     * 已过期语音搜索次数
     */
    private Integer expiredVoiceCount;

    /**
     *剩余查看答案限免次数
     */
    private Integer freeCount;

}
