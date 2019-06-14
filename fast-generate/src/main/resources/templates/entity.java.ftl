package ${package.entityPack};

<#list importPackages as pkg>
import ${pkg!};
</#list>
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.persistence.*;
<#if superEntityClassPackage??>
import ${superEntityClassPackage};
</#if>

/**
 * ${table.comment!}
 *
 * @author ${author}
 * @date ${date}
 */
@Data
    <#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
    <#else>
@EqualsAndHashCode(callSuper = false)
    </#if>
@Accessors(chain = true)
@Table(name="${table.tableName}")
<#if superEntityClass??>
public class ${entity} extends ${superEntityClass} implements Serializable{
<#else>
public class ${entity} implements Serializable {
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.columnFields as field>
    <#if field.comment!?length gt 0>
    /**
     * ${field.comment}
     */
    </#if>
    <#if field.keyFlag>
    <#-- 主键 -->
    @Id
        <#if field.identityFlag>
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        </#if>
    </#if>
    private ${field.fieldType} ${field.fieldName};
</#list>

}
