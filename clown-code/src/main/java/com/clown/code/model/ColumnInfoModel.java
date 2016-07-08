package com.clown.code.model;

/**
 * Created by lenli on 2016/7/4.
 *
 * @Author Libin
 * @Date 2016/7/4
 */
public class ColumnInfoModel {

    private String columnName;
    private String dataType;
    private String columnComment;
    private String columnModelName;


    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnModelName() {
        return columnModelName;
    }

    public void setColumnModelName(String columnModelName) {
        this.columnModelName = columnModelName;
    }
}
