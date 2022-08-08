package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 每日文章表
 * </p>
 *
 * @author xutao
 * @since 2021-07-29
 */
@Data
@Accessors(chain = true)
public class DayArticle  {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类id
     */
    private Integer classifyId;

    /**
     * 详情页用的标题
     */
    private String title;

    /**
     * 列表页用的标题
     */
    private String listTitle;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 访问次数
     */
    private Integer visitNum;

    /**
     * 作者
     */
    private String author;

    /**
     * 数据状态
     */
    private String status;

    /**
     * 审核uid
     */
    private String checkUid;

    /**
     * 审核用户名
     */
    private String checkUname;

    /**
     * 审核时间
     */
    private LocalDateTime checkTime;

    /**
     * 最后修改uid
     */
    private Integer upUid;

    /**
     * 最后修改用户名
     */
    private String upUname;

    /**
     * 更新时间
     */
    private LocalDateTime upTime;

    /**
     * 删除uid
     */
    private Integer deleteUid;

    /**
     * 删除用户名
     */
    private String deleteUname;

    /**
     * 更新时间
     */
    private LocalDateTime deleteTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建uid
     */
    private Integer createUid;

    /**
     * 创建用户名
     */
    private String createUname;

}
