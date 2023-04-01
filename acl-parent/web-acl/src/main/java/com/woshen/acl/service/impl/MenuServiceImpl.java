package com.woshen.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woshen.acl.entity.Menu;
import com.woshen.acl.mapper.MenuMapper;
import com.woshen.acl.service.IMenuService;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.webcommon.db.service.impl.BaseServiceImpl;
import com.woshen.common.webcommon.model.DataStatus;
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
public class MenuServiceImpl extends BaseServiceImpl<Integer, MenuMapper, Menu> implements IMenuService {

    @Override
    public QueryWrapper<Menu> getBaseWrapper(Menu queryData) {
        QueryWrapper<Menu> baseWrapper = super.getBaseWrapper(queryData);
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
    public Integer dosave(Menu queryData) {
        LocalDateTime now = LocalDateTime.now();
        if(queryData.getId() == null){
            queryData.setCreateTime(now);
            if(queryData.getPriority() == null){
                queryData.setPriority(50);
            }
            queryData.setStatus(DataStatus.NORMAL);
        }
        queryData.setUpdateTime(now);
        return super.dosave(queryData);
    }

}
