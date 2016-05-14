package com.clown.lightdb.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by len.li on 21/3/2016.
 */
public class MapperConfig {

    private static final int DEFAULT_MULTIPLYER = 37;
    private static final int DEFAULT_HASHCODE = 17;

    public final static String SELECT = "SELECT";
    public final static String DELETE = "DELETE";
    public final static String UPDATE = "UPDATE";
    public final static String INSERT = "INSERT";

    public final static String ORDER_ASC = "ASC";
    public final static String ORDER_DESC = "DESC";

    public final static String LIMIT_START = "Mysql_currentPage";
    public final static String LIMIT_SIZE = "Mysql_pageSize";
    public final static String SELECT_ALL = "COUNT";

    public final static String DEFAULT_PRIMARY_KEY = "id";

    public final static String DEFAULT_TABLE_ALIAS_NAME = "t";

    private String queryType="SELECT";

    private String primaryKey=DEFAULT_PRIMARY_KEY;

    private int currentPage  =1;
    private int pageSize = 1000;

    /**
     * where 条件设置
     */
    private List<Condition> conditionList = new ArrayList<Condition>();

    /**
     * 查询字段
     */
    private List<String> fields = new ArrayList<String>();
    private Object parameterModel;

    /**
     * left join查询集合
     */
    private List<Join> childJoin = new ArrayList<Join>();

    private String orderBy;

    /**
     * 表对象
     */
    private Class target;
    private int multiplier;
    private int hashcode;

    public MapperConfig() {
        this.multiplier = DEFAULT_MULTIPLYER;
        this.hashcode = DEFAULT_HASHCODE;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void addCondition(Condition...conditions){
        for(Condition condition:conditions){
            conditionList.add(condition);

        }
    }

    public void addFields(String...cols){
        for (String name : cols){
            this.fields.add(name);
        }
    }

    public void addJoin(Join...joins){
        for(Join join:joins){
           this.childJoin.add(join);

        }
    }

    public void addLimit(int currentPage,int pageSize){
        this.pageSize = pageSize>0?pageSize:this.pageSize;
        this.currentPage = currentPage>=1?currentPage:this.currentPage;
    }

    public void addOrderBy(String orderBy,String ord){
        this.orderBy = String.format("ORDER BY %s %s",orderBy,ord);
    }

    public Class getTarget() {
        return target;
    }

    public void setTarget(Class target) {
        this.target = target;
    }

    public void setTarget(String queryType,Class target) {
        this.target = target;
        this.queryType = queryType;
    }


    public String generateStatementName(Class parameterType,Class resultType,String endFix){

        if(null == endFix){
            endFix = "";
        }
        int joinHashCode = 0;
        int conditionHashCode=0;
        int fieldHashCode = 0;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.getTarget().getName());
        stringBuffer.append(this.getQueryType());
        if(null != parameterType){
            stringBuffer.append(parameterType.getSimpleName());
        }
        if(null != resultType){
            stringBuffer.append(resultType.getSimpleName());
        }

        if(getFields().size()>0){
            for(String str:getFields()){
                fieldHashCode = fieldHashCode+(this.multiplier*this.hashcode)+str.hashCode();
            }
        }

        if(getChildJoin().size()>0){
            for(Join join : getChildJoin()){
                joinHashCode =joinHashCode+ this.multiplier*this.hashcode+join.findHashCode();
            }
        }

        if(getConditionList().size()>0){
            for(Condition condition:getConditionList()){
                conditionHashCode = conditionHashCode+(this.multiplier*this.hashcode)+condition.findHashCode();
            }
        }

        return String.format("ca.alka.crud.mybatis.%s:%d:%d:%d:%d-%s",this.getTarget().getSimpleName(),
                stringBuffer.toString().hashCode(),
                fieldHashCode,
                joinHashCode,
                conditionHashCode,
                endFix.toUpperCase());
    }



    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public List<String> getFields() {
        return fields;
    }

    public Object getParameterModel() {
        return parameterModel;
    }

    public void setParameterModel(Object parameterModel) {
        this.parameterModel = parameterModel;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Map<String,Object> getConditionParameter(){

        Map<String,Object> result = new HashMap<String, Object>();
        result.put(LIMIT_START,(currentPage-1)*pageSize);
        result.put(LIMIT_SIZE,pageSize);
        if(getConditionList()!=null){
            for(Condition condition : getConditionList()){
                result.put(condition.getColunmName(),condition.getValues());
            }
        }
        return result;
    }

    public List<Join> getChildJoin() {
        return childJoin;
    }

    public static void main(String[] args) throws Exception{

        System.out.println(MapperConfig.class.getCanonicalName());

    }

    public String getOrderBy() {
        return orderBy;
    }
}
