package com.clown.lightdb.core;

import com.clown.lightdb.utils.BuildUtil;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by len.li on 3/2/2016.
 */
public class MySQLBuildNode implements BuildMyBatisNodeInter {

    private MapperConfig mapperConfig;
    private SqlSession sqlSession;

    private String defaultTablePrefix = MapperConfig.DEFAULT_TABLE_ALIAS_NAME;

    public MySQLBuildNode(MapperConfig mapperConfig, SqlSession sqlSession) {
        this.mapperConfig = mapperConfig;
        this.sqlSession = sqlSession;
    }

    @Override
    public SqlNode build() throws Exception {
        List<SqlNode> rootNode = new ArrayList<SqlNode>();
        StringBuffer selectStr;
        // build select node
        if(mapperConfig.getQueryType().equals(MapperConfig.SELECT)){
            selectStr = new StringBuffer();
            selectStr.append("SELECT ");
            selectStr.append(BuildUtil.parseFieldList(mapperConfig.getFields(),mapperConfig.getTarget(),false,getDefaultTablePrefix()));
            selectStr.append(" FROM ");
            selectStr.append(BuildUtil.getTableName(mapperConfig.getTarget()));
            selectStr.append(" as "+getDefaultTablePrefix());
            rootNode.add(new StaticTextSqlNode(selectStr.toString()));
        }
        // build update node
        if(mapperConfig.getQueryType().equals(MapperConfig.UPDATE)){
            selectStr = new StringBuffer();
            selectStr.append("UPDATE ");
            selectStr.append(BuildUtil.getTableName(mapperConfig.getTarget()));
            selectStr.append(" as "+getDefaultTablePrefix());
            rootNode.add(new StaticTextSqlNode(selectStr.toString()));
            rootNode.add(buildSetNode(sqlSession,mapperConfig.getParameterModel()));

        }
        // build delete node
        if(mapperConfig.getQueryType().equals(MapperConfig.DELETE)){
            selectStr = new StringBuffer();
            selectStr.append("DELETE FROM ");
            selectStr.append(BuildUtil.getTableName(mapperConfig.getTarget()));
//            selectStr.append(" as "+getDefaultTablePrefix());
            rootNode.add(new StaticTextSqlNode(selectStr.toString()));
        }
        // build insert node
        if(mapperConfig.getQueryType().equals(MapperConfig.INSERT)){
            selectStr = new StringBuffer();
            Map<String,SqlNode> insertMap = BuildUtil.parseFieldSqlNodeList(mapperConfig.getTarget());
            selectStr.append("INSERT INTO ");
            selectStr.append(BuildUtil.getTableName(mapperConfig.getTarget()));

            rootNode.add(new StaticTextSqlNode(selectStr.toString()));
            rootNode.add(new StaticTextSqlNode("("));
            rootNode.add(new TrimSqlNode(sqlSession.getConfiguration(),insertMap.get(BuildUtil.INSERT_COLUMNS_LIST),null,null,"",","));
            rootNode.add(new StaticTextSqlNode(") VALUES ("));
            rootNode.add(new TrimSqlNode(sqlSession.getConfiguration(),insertMap.get(BuildUtil.INSERT_COLUMNS_VALUES),null,null,"",","));
            rootNode.add(new StaticTextSqlNode(");"));

            return new MixedSqlNode(rootNode);
        }

        // build left join node
        if(mapperConfig.getQueryType().equals(MapperConfig.SELECT) && mapperConfig.getChildJoin().size()>0){
            StringBuffer joinString = new StringBuffer();
            for(Join join : mapperConfig.getChildJoin()){
                joinString.append(String.format(" LEFT JOIN %s as %s ON %s ",BuildUtil.getTableName(join.getTableModel()),join.getAliasName(),join.getCondition()));
            }
            rootNode.add(new StaticTextSqlNode(joinString.toString()));
        }

        // build where node
        if(!mapperConfig.getQueryType().equals(MapperConfig.INSERT) && mapperConfig.getConditionList().size()>0){
            List<SqlNode> ifNodelist = new ArrayList<SqlNode>();
            ifNodelist.add(new StaticTextSqlNode(" 1=1 "));
            String whereFormat = " AND %s.%s %s #{%s} ";
            if(mapperConfig.getQueryType().equals(MapperConfig.DELETE)){
                whereFormat = " AND %s %s #{%s} ";
            }
            StaticTextSqlNode staticTextSqlNode = null;
            for (Condition con : mapperConfig.getConditionList()){

                if(mapperConfig.getQueryType().equals(MapperConfig.DELETE)){
                    staticTextSqlNode  =new StaticTextSqlNode(String.format(whereFormat,con.getColunmName(),con.getComparison(),con.getColunmName()));
                }
                else{
                    staticTextSqlNode  =new StaticTextSqlNode(String.format(whereFormat,con.getAliasName(),con.getColunmName(),con.getComparison(),con.getColunmName()));
                }

                if(!con.isRequired()){
                    String conditionStr;
                    if(con.getValues() instanceof String){
                        conditionStr = String.format("%s !=null and %s !=''",con.getColunmName(),con.getColunmName());
                    }
                    else{
                        conditionStr = String.format("%s !=null",con.getColunmName());
                    }
                    ifNodelist.add(new IfSqlNode(staticTextSqlNode,conditionStr));
                }
                else {
                    ifNodelist.add(staticTextSqlNode);
                }
            }
            rootNode.add(new WhereSqlNode(sqlSession.getConfiguration(),new MixedSqlNode(ifNodelist)));

            //order by
            if(mapperConfig.getQueryType().equals(MapperConfig.SELECT)){
                if(mapperConfig.getOrderBy()!=null){
                    rootNode.add(new StaticTextSqlNode(mapperConfig.getOrderBy()));
                }
            }
        }
        return new MixedSqlNode(rootNode);
    }

    public SqlNode buildNormalNode() throws Exception{
        SqlNode normalNode = build();
        if(mapperConfig.getQueryType().equals(MapperConfig.SELECT)){
            List<SqlNode> rootNode = new ArrayList<SqlNode>();
            rootNode.add(normalNode);
            rootNode.add(new StaticTextSqlNode(String.format(" LIMIT #{%s},#{%s}",MapperConfig.LIMIT_START,MapperConfig.LIMIT_SIZE)));
            return new MixedSqlNode(rootNode);
        }
        return normalNode;
    }

    /**
     * 生成Count查询statement
     * @return
     * @throws Exception
     */
    public SqlNode buildCountNode() throws Exception{
        SqlNode normalNode = build();
        List<SqlNode> rootNode = new ArrayList<SqlNode>();
        rootNode.add(new StaticTextSqlNode("SELECT COUNT(*) FROM ("));
        rootNode.add(normalNode);
        rootNode.add(new StaticTextSqlNode(") as tt"));
        return new MixedSqlNode(rootNode);
    }

    /**
     * 配置update 结构
     * @param sqlSession
     * @param model
     * @return
     * @throws Exception
     */
    private SetSqlNode buildSetNode(SqlSession sqlSession,Object model) throws Exception{
        List<SqlNode> ifNodelist = new ArrayList<SqlNode>();
        Field[] datalist = model.getClass().getDeclaredFields();
        String conditionStr;
        StaticTextSqlNode staticTextSqlNode;
        String prefix = "_param";
        for(Field field : datalist){
            if(field.getName().equals("id")){
                continue;
            }
            staticTextSqlNode=new StaticTextSqlNode(String.format("%s.%s = #{%s.%s},",getDefaultTablePrefix(),BuildUtil.parseFieldName(field.getName()),prefix,field.getName()));
            if(field.getType().equals(String.class)){
                conditionStr = String.format("%s.%s !=null and %s.%s !=''",prefix,field.getName(),prefix,field.getName());
            }
            else {
                conditionStr = String.format("%s.%s !=null",prefix,field.getName());
            }
            ifNodelist.add(new IfSqlNode(staticTextSqlNode,conditionStr));
        }
       return new SetSqlNode(sqlSession.getConfiguration(),new MixedSqlNode(ifNodelist));

    }


    public String getDefaultTablePrefix() {
        return defaultTablePrefix;
    }

    public void setDefaultTablePrefix(String defaultTablePrefix) {
        this.defaultTablePrefix = defaultTablePrefix;
    }
}
