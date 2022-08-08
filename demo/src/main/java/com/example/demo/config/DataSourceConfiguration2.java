package com.example.demo.config;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/8/9 15:58
 * @Version: 1.0.0
 * @Description: 描述
 */
@Configuration
@MapperScan(basePackages = "com.example.demo.mapper.testTwo",sqlSessionFactoryRef = "sqlSessionFactory2",sqlSessionTemplateRef = "sqlSessionTemplate2")
public class DataSourceConfiguration2 {


    @Resource
    private DataSource dataSources;


    @Bean("sqlSessionFactory2")
    public SqlSessionFactory sqlSessionFactory2() throws Exception {
        DataSource dataSource = ((ShardingDataSource) dataSources).getDataSourceMap().get("testwo");
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("sqlSessionTemplate2")
    public SqlSessionTemplate sqlSessionTemplate1(@Qualifier("sqlSessionFactory2")SqlSessionFactory sqlSessionFactory2) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory2);
        return sqlSessionTemplate;
    }
}
