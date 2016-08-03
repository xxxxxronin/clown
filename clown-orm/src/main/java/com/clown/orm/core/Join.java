package com.clown.orm.core;


/**
 * Created by len.li on 22/3/2016.
 */
public class Join {
    private Class tableModel;
    private String condition;
    private String aliasName;



    public Join( Class tableModel, String condition, String aliasName) {
        this.tableModel = tableModel;
        this.condition = condition;
        this.aliasName = aliasName;
    }

    public Class getTableModel() {
        return tableModel;
    }

    public void setTableModel(Class tableModel) {
        this.tableModel = tableModel;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public int findHashCode(){
        return String.format("%s.%sON%s",getAliasName(),getTableModel().getName().toString(),getCondition()).hashCode();
    }
}
