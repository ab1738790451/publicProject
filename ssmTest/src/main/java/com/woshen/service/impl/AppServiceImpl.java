package com.woshen.service.impl;

import com.woshen.common.baseTempl.BaseServiceImpl;
import com.woshen.entity.App;
import com.woshen.mapper.AppMapper;
import com.woshen.service.IAppService;
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
public class AppServiceImpl extends BaseServiceImpl<Integer,AppMapper,App>  implements IAppService {

}
