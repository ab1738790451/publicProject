package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
 <#list table.fields as field>
  <#if field.keyFlag >
   <#assign keyPropertyType= field.propertyType />
  </#if>
 </#list>
public interface ${table.serviceName} extends BaseService<${keyPropertyType},${table.entityName}>  {

}
</#if>
