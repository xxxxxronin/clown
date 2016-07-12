<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE ${"mapper"} PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${config.packageNameDao}.${table.tableModelName?cap_first}${config.daoSuffix}">
    <!--*****************************************-->
    <!--获得${table.tableName?cap_first}所有字段-->
    <!--*****************************************-->
    <select id="find${table.tableModelName?cap_first}List" resultType="${config.packageNameModel}.${table.tableModelName?cap_first}Model">
        SELECT
            <#list cols as item>

                ${item.columnName} as ${item.columnModelName} <#if item_has_next>,</#if>
            </#list>
        FROM ${table.tableName}  WHERE 1=1
    </select>

    <!--*************************************-->
    <!--获得${table.tableName?cap_first}单条记录-->
    <!--*************************************-->
    <select id="find${table.tableModelName?cap_first}One" resultType="${config.packageNameModel}.${table.tableModelName?cap_first}Model">
        SELECT
            <#list cols as item>
            ${item.columnName} as ${item.columnModelName} <#if item_has_next >,</#if>
            </#list>
        FROM ${table.tableName}  WHERE 1=1 LIMIT 1;
    </select>

    <!--*************************************-->
    <!--批量删除${table.tableName}数据-->
    <!--*************************************-->
    <delete id="delete${table.tableModelName?cap_first}Batch" parameterType="java.util.List">

    </delete>


    <!--*************************************-->
    <!--更新${table.tableName}数据-->
    <!--*************************************-->
    <update id="update${table.tableModelName?cap_first}One" parameterType="${config.packageNameModel}.${table.tableModelName?cap_first}Model">
        UPDATE ${table.tableName}
        <set>
            <trim>
                <#list cols as item>
                    <#if item.dataType == "varchar" || item.dataType=="text">
                        <if test="query.${item.columnModelName}!=null and query.${item.columnModelName}!=''">`${item.columnName}` = ${"#"}{query.${item.columnModelName}},</if>
                    </#if>
                    <#if !(item.dataType == "varchar" || item.dataType=="text")>
                        <if test="query.${item.columnModelName}!=null">`${item.columnName}` = ${"#"}{query.${item.columnModelName}},</if>
                    </#if>
                </#list>
            </trim>
        </set>
    </update>


    <!--*************************************-->
    <!--新增${table.tableName}数据-->
    <!--*************************************-->
    <insert id="insert${table.tableModelName?cap_first}One" parameterType="${config.packageNameModel}.${table.tableModelName?cap_first}Model">
        INSERT INTO ${table.tableName} (
            <#list cols as item>
                `${item.columnName}` <#if item_has_next>,</#if>
            </#list>
        ) VALUES (
            <#list cols as item>
                ${"#"}{query.${item.columnModelName}} <#if item_has_next>,</#if>
            </#list>
        );
    </insert>

</mapper>