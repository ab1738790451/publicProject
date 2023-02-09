package com.woshen.mapper;

import com.woshen.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuhaibo
 * @version 1.0
 * @company woshen
 * @Date 2022/11/15 23:24
 * @description
 */
@Repository
public interface UserMapper {
    @Insert("insert into user(user_name,login_id,mobile_phone,nicke_name,email,head_img,password,create_time,update_time,status) " +
            "values(#{userName},#{login_id},#{mobile_phone},#{nickeName},#{email},#{headImg},#{password},#{createTime},#{updateTime},#{status})")
    void insert(User user);

    @Select("select * from user where status = 'NORMAL'")
    List<User> selectAll();

    @Select("select * from user where login_id = #{loginId}")
    User selectByLoginId(String loginId);

    @Select("select * from user where user_id = #{userId}")
    User selectByUserId(String userId);
}
