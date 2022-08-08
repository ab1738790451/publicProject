package com.example.demo.mapper.testOne;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.MemberUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/6/7 15:19
 * @Version: 1.0.0
 * @Description: 描述
 */
public interface MemberUserVoMapper {

    List<List<?>> selectPageByVoTotal(@Param("params") MemberUserVo memberUserVo);

    Long selectPageTotal(@Param("params") MemberUserVo memberUserVo);
}
