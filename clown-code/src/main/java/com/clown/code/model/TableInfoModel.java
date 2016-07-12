package com.clown.code.model;

/**
 * Created by lenli on 2016/7/4.
 *
 * @Author Libin
 * @Date 2016/7/4
 */
public class TableInfoModel {

    private String tableName;
    private String tableComment;
    private String tableModelName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getTableModelName() {
        return tableModelName;
    }

    public void setTableModelName(String tableModelName) {
        this.tableModelName = tableModelName;
    }
}
