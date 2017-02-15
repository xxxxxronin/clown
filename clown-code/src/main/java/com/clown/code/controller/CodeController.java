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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenli on 2016/7/4.
 *
 * @Author Libin
 * @Date 2016/7/4
 */

@Controller
@RequestMapping(value = "/code")
public class CodeController {

    @Resource
    private DataBaseInfoService dataBaseInfoService;

    @RequestMapping(value ={"","/index","/"})
    public String index(Model model) throws Exception{
        return "home/index";
    }


    /**
     * 获得所有表
     * @param dbName
     * @return
     */
    @RequestMapping(value = "/{dbname}/tables",method = RequestMethod.GET)
    @ResponseBody
    public List<TableInfoModel> getTableAll(@PathVariable("dbname")String dbName){
        try {
           return dataBaseInfoService.findAllTableNames(dbName);
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }


    /**
     * 获得所有字段
     * @param dbName
     * @param table
     * @return
     */
    @RequestMapping(value = "/{dbname}/{table}/column",method = RequestMethod.GET)
    @ResponseBody
    public List<ColumnInfoModel> getTableAll(@PathVariable("dbname")String dbName,@PathVariable("table")String table){
        try {
            return dataBaseInfoService.findColumnInfo(dbName,table);
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    @RequestMapping(value = "/app/{path:.*}",method = RequestMethod.GET)
    public void getAssets(@PathVariable("path") String path, HttpServletResponse response){


        InputStream inputStream =null;
        try {

            if(path.equals("code")){
                response.getWriter().write("");
                response.flushBuffer();
                response.getWriter().close();
                return;
            }

            inputStream =  CodeController.class.getResourceAsStream("/app/"+path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line+"\n");

            }
            response.getWriter().write(sb.toString());
            response.flushBuffer();
            response.getWriter().close();
        }
        catch (Exception e){
            System.out.println(path);
           e.printStackTrace();
        }
        finally {
            try {
                if(inputStream !=null){
                    inputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
