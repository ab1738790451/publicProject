package com.woshen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.baseTempl.BaseServiceImpl;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.entity.Role;
import com.woshen.mapper.RoleMapper;
import com.woshen.service.IRoleService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Integer, RoleMapper, Role> implements IRoleService {

    @Override
    public QueryWrapper<Role> getBaseWrapper(Role queryData) {
        QueryWrapper<Role> baseWrapper = super.getBaseWrapper(queryData);
        String createStartTime = (String)queryData.getQueryParam("createStartTime");
        if(StringUtils.isNotBlank(createStartTime)){
            baseWrapper.gt("create_time",createStartTime);
        }
        String createEndTime = (String)queryData.getQueryParam("createEndTime");
        if(StringUtils.isNotBlank(createEndTime)){
            baseWrapper.gt("create_time",createEndTime);
        }

        String updateStartTime = (String)queryData.getQueryParam("updateStartTime");
        if(StringUtils.isNotBlank(updateStartTime)){
            baseWrapper.gt("update_time",updateStartTime);
        }

        String updateEndTime = (String)queryData.getQueryParam("updateEndTime");
        if(StringUtils.isNotBlank(updateEndTime)){
            baseWrapper.gt("update_time",updateEndTime);
        }
        return baseWrapper;
    }

    @Override
    public Integer dosave(Role queryData) {
        LocalDateTime now = LocalDateTime.now();
        if(queryData.getId() == null){
            queryData.setCreateTime(now);
            queryData.setStatus(DataStatus.NORMAL);
        }
        queryData.setUpdateTime(now);
        return super.dosave(queryData);
    }
}
