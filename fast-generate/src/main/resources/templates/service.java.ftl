package ${package.servicePack};


<#if superServiceClassPackage??>
import ${package.entityPack}.${entity};
import ${superServiceClassPackage};
</#if>

/**
 * ${table.comment!} 服务类
 *
 * @author ${author}
 * @date ${date}
 */
<#if superControllerClass??>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
<#else>
public interface ${table.serviceName}  {
</#if>
}