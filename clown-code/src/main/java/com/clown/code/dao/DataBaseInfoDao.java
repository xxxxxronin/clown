package com.clown.code.dao;

import com.clown.code.model.ColumnInfoModel;
import com.clown.code.model.TableInfoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lenli on 2016/7/4.
 *
 * @Author Libin
 * @Date 2016/7/4
 */
public interface DataBaseInfoDao {

    public List<TableInfoModel> findAllTableNames(@Param("dbName") String dbname) throws Exception;

    public List<ColumnInfoModel> findColumnInfo(@Param("dbName") String dbName,@Param("tableName") String tableName) throws Exception;
}
