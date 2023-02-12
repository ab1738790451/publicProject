package com.woshen.service.impl;

import com.woshen.common.baseTempl.BaseServiceImpl;
import com.woshen.entity.Role;
import com.woshen.mapper.RoleMapper;
import com.woshen.service.IRoleService;
import org.springframework.stereotype.Service;

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

}
