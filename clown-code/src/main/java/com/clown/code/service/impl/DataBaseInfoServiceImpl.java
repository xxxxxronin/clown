package com.clown.code.service.impl;

import com.clown.code.dao.DataBaseInfoDao;
import com.clown.code.model.ColumnInfoModel;
import com.clown.code.model.TableInfoModel;
import com.clown.code.service.DataBaseInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lenli on 2016/7/8.
 *
 * @Author Libin
 * @Date 2016/7/8
 */
@Service
public class DataBaseInfoServiceImpl implements DataBaseInfoService {

    @Resource
    private DataBaseInfoDao dataBaseInfoDao;

    public List<TableInfoModel> findAllTableNames(String dbname) throws Exception {
        List<TableInfoModel> tableInfoModelList = dataBaseInfoDao.findAllTableNames(dbname);
        return tableInfoModelList;
    }

    public List<ColumnInfoModel> findColumnInfo(String dbName, String tableName) throws Exception {
        return dataBaseInfoDao.findColumnInfo(dbName,tableName);
    }
}
