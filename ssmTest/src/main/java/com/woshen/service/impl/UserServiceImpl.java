package com.woshen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woshen.common.base.utils.ByteUtil;
import com.woshen.common.base.utils.StringUtils;
import com.woshen.common.baseTempl.BaseServiceImpl;
import com.woshen.common.constants.AclAuthKeyNs;
import com.woshen.common.constants.UserType;
import com.woshen.common.redis.utils.RedisUtil;
import com.woshen.common.webcommon.model.DataStatus;
import com.woshen.entity.User;
import com.woshen.entity.UserRole;
import com.woshen.mapper.UserMapper;
import com.woshen.service.IUserRoleService;
import com.woshen.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author liuhaibo
 * @since 2023-02-12
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<Integer, UserMapper, User> implements IUserService {

    @Resource
    private IUserRoleService userRoleServiceImpl;

    @Override
    public QueryWrapper<User> getBaseWrapper(User queryData) {
        QueryWrapper<User> baseWrapper = super.getBaseWrapper(queryData);
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
        String currId = (String)queryData.getQueryParam("currId");
        if(StringUtils.isNotBlank(currId)){
            baseWrapper.ne("id",Integer.valueOf(currId));
        }
        return baseWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer dosave(User queryData) {
        LocalDateTime now = LocalDateTime.now();
        Integer id = queryData.getId();
        if(queryData.getId() == null){
            queryData.setCreateTime(now);
            queryData.setStatus(DataStatus.NORMAL);
        }
        if(StringUtils.isNotBlank(queryData.getPassword())){
           queryData.setPassword(ByteUtil.byteToHexadecimal(DigestUtils.md5Digest(queryData.getPassword().getBytes())));
        }
        queryData.setUpdateTime(now);
        Integer pk = super.dosave(queryData);
        UserRole userRole = new UserRole();
        if(id == null && queryData.getUserType() == null){
            userRole.setUserType(UserType.ORDINARY);
        }else{
            userRole.setUserType(queryData.getUserType());
        }
        userRole.setId(pk);
        userRole.setUserType(queryData.getUserType());
        if(!CollectionUtils.isEmpty(queryData.getAppIds())){
            userRole.setAppIds(String.join(",",queryData.getAppIds()));
        }
        userRoleServiceImpl.dosave(userRole);
        if(id != null){
            RedisUtil.delKey(AclAuthKeyNs.ACL_USER,id);
        }
        return pk;
    }

    @Override
    public void del(Integer... pks) {
        if(pks != null && pks.length >0){
            for (Integer pk:pks
                 ) {
                RedisUtil.delKey(AclAuthKeyNs.ACL_USER,pk);
            }
        }
        super.del(pks);
    }
}
