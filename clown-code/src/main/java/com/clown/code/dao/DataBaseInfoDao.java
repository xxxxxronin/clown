package com.clown.code.dao;

import com.clown.code.model.ColumnInfoModel;
import com.clown.code.model.TableInfoModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lenli on 2016/7/4.
 *
 * @Author Libin
 * @Date 2016/7/4
 */
public interface DataBaseInfoDao {


    /**
     * 获得指定数据库所有的表列表
     * @param dbname
     * @return
     * @throws Exception
     */
    @Select("select `table_name` as tableName,TABLE_COMMENT as tableComment from information_schema.tables where table_schema=#{dbName} and table_type='base table'")
    public List<TableInfoModel> findAllTableNames(@Param("dbName") String dbname) throws Exception;


    /**
     * 获得指定表的所有的字段信息
     * @param dbName
     * @param tableName
     * @return
     * @throws Exception
     */
    @Select("select column_name as columnName,DATA_TYPE as `dataType`,COLUMN_COMMENT as columnComment from information_schema.columns where table_schema=#{dbName} and table_name=#{tableName}")
    public List<ColumnInfoModel> findColumnInfo(@Param("dbName") String dbName,@Param("tableName") String tableName) throws Exception;

}
