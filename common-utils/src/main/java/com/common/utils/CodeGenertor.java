package com.common.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
*@company woshen
*@author liuhaibo
*@Date 2023/1/27 19:46
*@version 1.0
*@description
*/
public class CodeGenertor {
    static String propertiesPath = "application.properties";

    public static void main(String[] args) throws IOException {
        String projectPath = System.getProperty("user.dir");
        Properties properties = new Properties();
        properties.load(new FileInputStream(projectPath + "/src/main/resources/" +propertiesPath));
        // 数据源配置
        DataSourceConfig dataSourceConfig = new  DataSourceConfig.Builder(properties.getProperty("datasource.url"),properties.getProperty("datasource.username"),properties.getProperty("datasource.password")).build();
        AutoGenerator mpg = new AutoGenerator(dataSourceConfig);
        // 全局配置
        GlobalConfig.Builder builder = new GlobalConfig.Builder();
        builder.outputDir(projectPath + properties.getProperty("product.path"));//设置代码生成路径
        builder.fileOverride();//是否覆盖以前文件
        builder.openDir(false);
        builder.author(properties.getProperty("author"));//设置项目作者名称
        mpg.global(builder.build());



        // 包配置
        PackageConfig.Builder pc = new PackageConfig.Builder();

        pc.parent(properties.getProperty("package"));
        pc.mapper("mapper");
        pc.xml("mapper.xml");
        pc.entity("entity");
        pc.service("service");
        pc.serviceImpl("service.impl");
        pc.controller("controller");
        mpg.packageInfo(pc.build());

        // 策略配置
        StrategyConfig.Builder sc = new StrategyConfig.Builder();
        sc.entityBuilder().enableLombok();
        sc.entityBuilder().idType(IdType.AUTO);
        sc.entityBuilder().naming(NamingStrategy.underline_to_camel);
        sc.mapperBuilder().enableBaseColumnList();
        sc.mapperBuilder().enableBaseResultMap();
        //sc.controllerBuilder().superClass()
        sc.addInclude(properties.getProperty("table"));
        mpg.strategy(sc.build());
        // 生成代码
        mpg.execute(new FreemarkerTemplateEngine());
    }

}
