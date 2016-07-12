package com.clown.code.common;

/**
 * Created by lenli on 2016/7/8.
 *
 * @Author Libin
 * @Date 2016/7/8
 */
public class BaseConfig {

    private String dirPath;

    /**
     * 项目根包
     */
    private String packageName;

    /**
     * 实体包路径
     */
    private String packageNameModel;
    /**
     * 实体后缀名
     */
    private String modelSuffix = "Model";

    /**
     * Dao 层Java类后缀
     */
    private String daoSuffix = "Dao";
    private String serviceSuffix = "Service";
    private String controllerSuffix = "Controller";

    /**
     * dao接口包路径
     */
    private String packageNameDao;

    /**
     * Service 层包路径
     */
    private String packageNameService;

    /**
     * Controller层包路径
     */
    private String packageNameController;
    /**
     * 作者名称
     */
    private String authorName = "Clow Auto Code";

    private String useDbName;
    private String useTableName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageNameModel() {
        return packageNameModel;
    }

    public void setPackageNameModel(String packageNameModel) {
        this.packageNameModel = packageNameModel;
    }

    public String getPackageNameDao() {
        return packageNameDao;
    }

    public void setPackageNameDao(String packageNameDao) {
        this.packageNameDao = packageNameDao;
    }

    public String getPackageNameService() {
        return packageNameService;
    }

    public void setPackageNameService(String packageNameService) {
        this.packageNameService = packageNameService;
    }

    public String getPackageNameController() {
        return packageNameController;
    }

    public void setPackageNameController(String packageNameController) {
        this.packageNameController = packageNameController;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getUseDbName() {
        return useDbName;
    }

    public void setUseDbName(String useDbName) {
        this.useDbName = useDbName;
    }

    public String getUseTableName() {
        return useTableName;
    }

    public void setUseTableName(String useTableName) {
        this.useTableName = useTableName;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getModelSuffix() {
        return modelSuffix;
    }

    public void setModelSuffix(String modelSuffix) {
        this.modelSuffix = modelSuffix;
    }



    public String getServiceSuffix() {
        return serviceSuffix;
    }

    public void setServiceSuffix(String serviceSuffix) {
        this.serviceSuffix = serviceSuffix;
    }

    public String getControllerSuffix() {
        return controllerSuffix;
    }

    public void setControllerSuffix(String controllerSuffix) {
        this.controllerSuffix = controllerSuffix;
    }

    public String getDaoSuffix() {
        return daoSuffix;
    }

    public void setDaoSuffix(String daoSuffix) {
        this.daoSuffix = daoSuffix;
    }
}
