package com.clown.code.service;

import com.clown.code.common.BaseConfig;
import com.clown.code.model.ColumnInfoModel;
import com.clown.code.model.CommonModel;
import com.clown.code.model.TableInfoModel;

import java.util.List;

/**
 * Created by lenli on 2016/7/8.
 *
 * @Author Libin
 * @Date 2016/7/8
 */
public interface DataBaseInfoService {

    public List<TableInfoModel> findAllTableNames( String dbname) throws Exception;

    public List<ColumnInfoModel> findColumnInfo(String dbName, String tableName) throws Exception;

    public CommonModel getBaseConfig(BaseConfig config) throws Exception;


}
