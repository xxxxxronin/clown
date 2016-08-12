package com.clown.code.controller;

import com.clown.code.common.BaseConfig;
import com.clown.code.model.ColumnInfoModel;
import com.clown.code.model.CommonModel;
import com.clown.code.model.TableInfoModel;
import com.clown.code.service.DataBaseInfoService;
import com.clown.code.utils.ConvertUtil;
import com.clown.framework.DefaultAjaxResult;
import com.clown.framework.utils.FreemarkerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenli on 2016/7/4.
 *
 * @Author Libin
 * @Date 2016/7/4
 */

@Controller
@RequestMapping(value = "")
public class CodeController {

    @Resource
    private DataBaseInfoService dataBaseInfoService;

    @RequestMapping(value ={"","/index","/"})
    public String index(Model model) throws Exception{
        return "home/index";
    }

    /**
     * 根据表名生成实体
     * @param dbName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/build/model/{dbname}",method = RequestMethod.GET)
    @ResponseBody
    public DefaultAjaxResult buildModels(@PathVariable("dbname")String dbName) throws Exception{
        DefaultAjaxResult defaultAjaxResult = new DefaultAjaxResult();
        BaseConfig baseConfig = new BaseConfig();
        baseConfig.setUseDbName(dbName);
        baseConfig.setDirPath("/home/lenli/documents/");
        CommonModel commonModel = dataBaseInfoService.getBaseConfig(baseConfig);
        for (TableInfoModel tableInfoModel : commonModel.getTableList()){
            String tableName = ConvertUtil.converName("tsys_",tableInfoModel.getTableName()," ");
            tableInfoModel.setTableModelName(tableName);

            List<String> dataType = new ArrayList<String>();
            List<ColumnInfoModel> columnInfoModelList = dataBaseInfoService.findColumnInfo(dbName,tableInfoModel.getTableName());
            for (ColumnInfoModel columnInfoModel : columnInfoModelList){
                columnInfoModel.setColumnModelName(ConvertUtil.converName("f_",columnInfoModel.getColumnName()," "));
                if(columnInfoModel.getDataType().equals("date") || columnInfoModel.getDataType().equals("datetime")){
                    if(!dataType.contains(columnInfoModel.getDataType())){
                        dataType.add(columnInfoModel.getDataType());
                    }
                }
            }
            commonModel.setCols(columnInfoModelList);
            commonModel.setColType(dataType);
            commonModel.setTable(tableInfoModel);

            FreemarkerUtil.createFile("build/model.ftl",commonModel,
                    ConvertUtil.converModelPath(
                    commonModel.getConfig().getDirPath(),
                    commonModel.getConfig().getPackageNameModel(),
                    commonModel.getTable().getTableModelName(),
                    commonModel.getConfig().getModelSuffix(),"java")
            );

            FreemarkerUtil.createFile("build/dao.ftl",commonModel,
                    ConvertUtil.converModelPath(
                            commonModel.getConfig().getDirPath(),
                            commonModel.getConfig().getPackageNameDao(),
                            commonModel.getTable().getTableModelName(),
                            commonModel.getConfig().getDaoSuffix(),"java")
            );

            FreemarkerUtil.createFile("build/mapper.ftl",commonModel,
                    ConvertUtil.converModelPath(
                            commonModel.getConfig().getDirPath(),
                            commonModel.getConfig().getPackageNameDao(),
                            commonModel.getTable().getTableModelName(),
                            commonModel.getConfig().getDaoSuffix(),"xml")
            );
        }
        return defaultAjaxResult;
    }


    /**
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/build/mapper/{dbname}")
    @ResponseBody
    public DefaultAjaxResult buildMapper(@PathVariable("dbname")String dbName) throws Exception{
        DefaultAjaxResult defaultAjaxResult = new DefaultAjaxResult();
        return defaultAjaxResult;
    }


}
