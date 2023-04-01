package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if table.entityName?? && table.entityName != "">${table.entityName?uncap_first}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
<#list table.fields as field>
 <#if field.keyFlag >
  <#assign keyPropertyType= field.propertyType />
 </#if>
</#list>
public class ${table.controllerName} extends AbstractController<${keyPropertyType},${table.entityName}> {
</#if>

 @Resource
 private ${table.serviceName} ${table.serviceImplName?uncap_first};

 @Override
 public ${table.serviceName} getService() {
 return ${table.serviceImplName?uncap_first};
 }

}
</#if>
