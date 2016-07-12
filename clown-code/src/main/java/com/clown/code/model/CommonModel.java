package com.clown.code.model;

import com.clown.code.common.BaseConfig;

import java.util.List;

/**
 * Created by lenli on 2016/7/12.
 *
 * @Author Libin
 * @Date 2016/7/12
 */
public class CommonModel {

    private BaseConfig config;
    private TableInfoModel table;
    private List<ColumnInfoModel> cols;

    private List<String> colType;
    private List<TableInfoModel> tableList;

    public BaseConfig getConfig() {
        return config;
    }

    public void setConfig(BaseConfig config) {
        this.config = config;
    }

    public TableInfoModel getTable() {
        return table;
    }

    public void setTable(TableInfoModel table) {
        this.table = table;
    }

    public List<ColumnInfoModel> getCols() {
        return cols;
    }

    public void setCols(List<ColumnInfoModel> cols) {
        this.cols = cols;
    }

    public List<String> getColType() {
        return colType;
    }

    public void setColType(List<String> colType) {
        this.colType = colType;
    }

    public List<TableInfoModel> getTableList() {
        return tableList;
    }

    public void setTableList(List<TableInfoModel> tableList) {
        this.tableList = tableList;
    }
}
