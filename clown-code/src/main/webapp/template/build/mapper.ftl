<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.clown.code.dao.${tableModelName?cap_first}Dao">
    <#assign columnListSize="${columnList?size}"/>
    <!--*****************************************-->
    <!--获得${tableName?cap_first}所有字段-->
    <!--*****************************************-->
    <select id="find${tableModelName?cap_first}List" resultType="${packageName}.model.${tableModelName?cap_first}Model">
        SELECT
            <#list columnList as cols>
                ${cols.columnName} as ${cols.columnModelName} <#if cols_index<(columnListSize-1)>,</#if>
            </#list>
        FROM ${tableName}  WHERE 1=1
    </select>

    <!--*************************************-->
    <!--获得${tableName?cap_first}单条记录-->
    <!--*************************************-->
    <select id="find${tableModelName?cap_first}One" resultType="${packageName}.model.${tableModelName?cap_first}Model">
        SELECT
            <#list columnList as cols>
            ${cols.columnName} as ${cols.columnModelName} <#if cols_index<(columnListSize-1)>,</#if>
            </#list>
        FROM ${tableName}  WHERE 1=1 LIMIT 1;
    </select>

    <!--*************************************-->
    <!--批量删除${tableName}数据-->
    <!--*************************************-->
    <delete id="delete${tableModelName?cap_first}Batch" parameterType="java.util.List">

    </delete>


    <!--*************************************-->
    <!--更新${tableName}数据-->
    <!--*************************************-->
    <update id="update${tableModelName?cap_first}One" parameterType="${packageName}.model.${tableModelName?cap_first}Model">
        UPDATE ${tableName}
        <set>
            <trim>
                <#list columnList as cols>
                    <#if cols.dataType == "varchar" || cols.dataType=="text">
                        <if test="query.${cols.columnModelName}!=null and query.${cols.columnModelName}!=''">`${cols.columnName}` = ${"#"}{query.${cols.columnModelName}},</if>
                    </#if>
                    <#if !(cols.dataType == "varchar" || cols.dataType=="text")>
                        <if test="query.${cols.columnModelName}!=null">`${cols.columnName}` = ${"#"}{query.${cols.columnModelName}},</if>
                    </#if>
                </#list>
            </trim>
        </set>
    </update>


    <!--*************************************-->
    <!--新增${tableName}数据-->
    <!--*************************************-->
    <insert id="insert${tableModelName?cap_first}One" parameterType="${packageName}.model.${tableModelName?cap_first}Model">
        INSERT INTO ${tableName} (
            <#list columnList as cols>
                `${col.columnName}` <#if cols_index<(columnListSize-1)>,</#if>
            </#list>
        ) VALUES (
            <#list columnList as cols>
                ${"#"}{query.${cols.columnModelName}} <#if cols_index<(columnListSize-1)>,</#if>
            </#list>
        );
    </insert>

</mapper>