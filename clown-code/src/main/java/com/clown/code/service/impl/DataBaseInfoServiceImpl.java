package com.clown.code.service.impl;

import com.clown.code.common.BaseConfig;
import com.clown.code.dao.DataBaseInfoDao;
import com.clown.code.model.ColumnInfoModel;
import com.clown.code.model.CommonModel;
import com.clown.code.model.TableInfoModel;
import com.clown.code.service.DataBaseInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public CommonModel getBaseConfig(BaseConfig config) throws Exception {
        CommonModel commonModel = new CommonModel();
        if(StringUtils.isEmpty(config.getPackageName())){
            config.setPackageName("com.clown.app");
        }

        if(StringUtils.isEmpty(config.getPackageNameModel())){
            config.setPackageNameModel(config.getPackageName()+".model");
        }

        if(StringUtils.isEmpty(config.getPackageNameDao())){
            config.setPackageNameDao(config.getPackageName()+".dao");
        }

        if(StringUtils.isEmpty(config.getPackageNameService())){
            config.setPackageNameService(config.getPackageName()+".service");
        }

        if(StringUtils.isEmpty(config.getPackageNameController())){
            config.setPackageNameController(config.getPackageName()+".controller");
        }
        commonModel.setConfig(config);
        // 从数据获得数据结构信息
        List<TableInfoModel> tableInfoModelList = dataBaseInfoDao.findAllTableNames(config.getUseDbName());
        commonModel.setTableList(tableInfoModelList);
        return commonModel;
    }
}
