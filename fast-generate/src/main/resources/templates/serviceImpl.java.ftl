package ${package.serviceImplPack};

import ${package.servicePack}.${table.serviceName};
<#if superServiceImplClassPackage??>
import ${package.entityPack}.${entity};
import ${package.mapperPack}.${table.mapperName};
import ${superServiceImplClassPackage};
</#if>
import org.springframework.stereotype.Service;

/**
 * ${table.comment!} 服务实现类
 *
 * @author ${author}
 * @date ${date}
 */
@Service
<#if superServiceImplClassPackage??>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
<#else>
public class ${table.serviceImplName} implements ${table.serviceName} {
</#if>
}