package com.woshen.acl.common.constants;

import com.woshen.common.redis.constants.RedisKeyType;
import com.woshen.common.redis.core.RedisKeyNs;

/**
 * @Author: liuhaibo
 * @Date: 2023/3/2 15:44
 * @Version: 1.0.0
 * @Description: 描述
 */
public enum AclAuthKeyNs implements RedisKeyNs {
    ACL_USER(60*60,RedisKeyType.HASH,"acl用户信息"),
    ACL_URL_ACCESS_ROLES(60*60,RedisKeyType.HASH,"拥有url访问权限的角色"),
    ACL_USER_MENU(60*60,RedisKeyType.STRING,"用户菜单");

    private long expire;

    private String nameSpace;

    private RedisKeyType keyType;

    private String descr;

    AclAuthKeyNs(long expire,RedisKeyType keyType,String descr){
        this.expire = expire;
        this.keyType = keyType;
        this.nameSpace = this.name();
        this.descr = descr;
    }
    @Override
    public long getExpire() {
        return expire;
    }

    @Override
    public String getNameSpace() {
        return nameSpace;
    }

    @Override
    public RedisKeyType getKeyType() {
        return keyType;
    }

    public String getDescr() {
        return descr;
    }
}
