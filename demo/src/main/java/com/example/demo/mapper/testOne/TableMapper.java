package com.example.demo.mapper.testOne;

import com.example.demo.entity.Table;
import com.example.demo.entity.Tb;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/5/17 11:31
 * @Version: 1.0.0
 * @Description: 描述
 */
@Mapper
public interface TableMapper {
    //@Insert("insert into table_1(id,name,age,sex) values(#{params.id},#{params.name},#{params.age},#{params.sex})")
    void addTable(@Param("params") Table table);

     List<Table> queryAll();
    @Insert("INSERT INTO customer_service_operation_log (uid,user_name,log_type,oper_time,oper_id,oper_name,ip,remark,data_id,data_type,create_time)" +
            "    SELECT 4,'uHoyAzTT30NsO+PVGul89w==','DISABLED','2021-6-23 14:08:21','10000','超级管理员','127.0.0.1','',203,'update','2021-6-23 14:08:21'" +
            "    WHERE not exists (SELECT id FROM customer_service_operation_log where data_type = 'update' and data_id = 2)")
    void add();


}
