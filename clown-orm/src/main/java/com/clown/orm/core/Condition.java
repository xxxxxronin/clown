package com.clown.orm.core;

/**
 * Created by len.li on 3/2/2016.
 */
public class Condition {

    public static final String Eq="=";
    public static final String neq="&lt;&gt;";
    public static final String Gl="&gt;";
    public static final String Gle="&gt;=";
    public static final String Tl="&lt;";
    public static final String Tle="&lt;=";

    private boolean required = false;
    /**
     * 字段或列名
     */
    private String colunmName;
    /**
     * 条件值
     */
    private Object values;
    /**
     * 比较操作符 = 、>、>=、<、<=、%%、
     */
    private String comparison;

    /**
     * 实体属性名称
     */
    private String attributeName;

    private String aliasName;


    public Condition(String colunmName, Object values) {
        this(MapperConfig.DEFAULT_TABLE_ALIAS_NAME,colunmName,colunmName,values,Condition.Eq,false);
    }

    public Condition(String colunmName, Object values,String comparison) {
        this(MapperConfig.DEFAULT_TABLE_ALIAS_NAME,colunmName,colunmName,values,comparison,false);
    }

    public Condition(String aliasName,String colunmName, Object values) {
        this(aliasName,colunmName,colunmName,values,Condition.Eq,false);
    }

    public Condition(String colunmName, Object values,boolean required) {
        this(MapperConfig.DEFAULT_TABLE_ALIAS_NAME,colunmName,colunmName,values,Condition.Eq,required);
    }

    public Condition(String aliasName,String colunmName, Object values,boolean required) {
        this(aliasName,colunmName,colunmName,values,Condition.Eq,required);
    }



    public Condition(String aliasName,String attributeName, String colunmName, Object values, String comparison, boolean  required) {
        this.aliasName = aliasName;
        this.colunmName = colunmName;
        this.values = values;
        this.comparison = comparison;
        this.attributeName = attributeName;
        this.required = required;
    }

    public String getColunmName() {
        return colunmName;
    }



    public String getComparison() {
        return comparison;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public Object getValues() {
        return values;
    }

    public boolean isRequired() {
        return required;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public int findHashCode(){
        return String.format("%s.%s&&%s",getAliasName(),getColunmName(),getComparison()).hashCode();
    }
}
