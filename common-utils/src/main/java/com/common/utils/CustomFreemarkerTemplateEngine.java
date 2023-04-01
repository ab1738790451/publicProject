package com.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: liuhaibo
 * @Date: 2023/3/15 9:49
 * @Version: 1.0.0
 * @Description: 自定义扩展模板引擎
 */
public class CustomFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

    protected void outputListHtml( TableInfo tableInfo, Map<String, Object> objectMap) {
        ConfigBuilder config = (ConfigBuilder) objectMap.get("config");
        GlobalConfig globalConfig = config.getGlobalConfig();
        PackageConfig packageConfig = config.getPackageConfig();
        String entityName = tableInfo.getEntityName();
        String moduleName = entityName.substring(0,1).toLowerCase() + entityName.substring(1);
        String fileName = "list";
        String parent = packageConfig.getParent();
        String path = globalConfig.getOutputDir() + "/" + parent.replace(".","/") +"/" + moduleName;
        if (StringUtils.isNotBlank(entityName) && StringUtils.isNotBlank(path)) {
            objectMap.put("moduleName",moduleName);
            this.getTemplateFilePath((template) -> {
                return "/list.html";
            }).ifPresent((entity) -> {
                String entityFile = String.format(path + File.separator + "%s" + ".html", fileName);
                this.outputFile(new File(entityFile), objectMap, entity);
            });
        }

    }

    protected void outputEditHtml( TableInfo tableInfo,  Map<String, Object> objectMap) {
        ConfigBuilder config = (ConfigBuilder) objectMap.get("config");
        GlobalConfig globalConfig = config.getGlobalConfig();
        PackageConfig packageConfig = config.getPackageConfig();
        String entityName = tableInfo.getEntityName();
        String fileName = "edit";
        String parent = packageConfig.getParent();
        String moduleName = entityName.substring(0,1).toLowerCase() + entityName.substring(1);
        String path = globalConfig.getOutputDir() + "/" + parent.replace(".","/") +"/"+ moduleName;
        if (StringUtils.isNotBlank(entityName) && StringUtils.isNotBlank(path)) {
            objectMap.put("moduleName",moduleName);
            this.getTemplateFilePath((template) -> {
                return "/edit.html";
            }).ifPresent((entity) -> {
                String entityFile = String.format(path + File.separator + "%s" + ".html", fileName);
                this.outputFile(new File(entityFile), objectMap, entity);
            });
        }

    }

    @Override
    public AbstractTemplateEngine batchOutput() {
        try {
            ConfigBuilder config = this.getConfigBuilder();
            List<TableInfo> tableInfoList = config.getTableInfoList();
            tableInfoList.forEach((tableInfo) -> {
                Map<String, Object> objectMap = this.getObjectMap(config, tableInfo);
                Optional.ofNullable(config.getInjectionConfig()).ifPresent((t) -> {
                    t.beforeOutputFile(tableInfo, objectMap);
                });
                this.outputEntity(tableInfo, objectMap);
                this.outputMapper(tableInfo, objectMap);
                this.outputService(tableInfo, objectMap);
                this.outputController(tableInfo, objectMap);
                this.outputListHtml(tableInfo,objectMap);
                this.outputEditHtml(tableInfo,objectMap);
            });
            return this;
        } catch (Exception var3) {
            throw new RuntimeException("无法创建文件，请检查配置信息！", var3);
        }
    }
}
