package ${package.mapperPack!};


<#if superMapperClassPackage??>
import ${package.entityPack}.${entity};
import ${superMapperClassPackage};
</#if>

/**
 * ${table.comment!} Mapper 接口
 *
 * @author ${author}
 * @date ${date}
 */
@org.apache.ibatis.annotations.Mapper
<#if superControllerClass??>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {
<#else>
public interface ${table.mapperName}  {
</#if>
}