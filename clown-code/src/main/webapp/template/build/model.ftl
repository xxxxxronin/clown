package ${config.packageNameModel};
<#list colType as row>
    <#if row == "datetime">
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
    </#if>
</#list>
/**
* Created by ${config.authorName} on 2016/7/4.
*
* @Author ${config.authorName}
* @Date 2016/7/4
*/
public class ${table.tableModelName?cap_first}Model {

<#list cols as col>
<#if col.dataType=="varchar">private String ${col.columnName}; //${col.columnComment}</#if>
<#if col.dataType=="date" || col.dataType=="datetime">
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
private Date ${col.columnName}; //${col.columnComment}
</#if>
<#if col.dataType=="int" || col.dataType == "smallint" || col.dataType=="integer" || col.dataType=="bigint" || col.dataType=="tinyint">private Integer ${col.columnName}; //${col.columnComment}</#if>
</#list>


<#list cols as item>
<#assign colType ="String" />
    <#if item.dataType=="date" || item.dataType=="datetime">
        <#assign colType="Date" />
    </#if>
    <#if item.dataType=="int" || item.dataType == "smallint" || item.dataType=="integer" || item.dataType=="bigint" || item.dataType=="tinyint">
        <#assign colType="Integer" />
    </#if>
public void set${item.columnName?cap_first}(${colType} ${item.columnName}) {
    this.${item.columnName} = ${item.columnName};
}

public ${colType} get${item.columnName?cap_first}() {
    return ${item.columnName};
}
</#list>

}