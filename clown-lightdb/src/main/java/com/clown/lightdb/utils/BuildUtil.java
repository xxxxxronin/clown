package com.clown.lightdb.utils;

import com.clown.lightdb.annotations.LightColumn;
import com.clown.lightdb.annotations.LightTable;
import org.apache.ibatis.javassist.compiler.ast.Symbol;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by len.li on 21/3/2016.
 */
public class BuildUtil {

    public final static String INSERT_COLUMNS_LIST="INSERT_COLUMNS_LIST";
    public final static String INSERT_COLUMNS_VALUES="INSERT_COLUMNS_VALUES";

    /**
     * 获取实体上注解的表名
     * @param tClass
     * @return
     * @throws Exception
     */
    public static String getTableName(Class tClass) throws Exception{
        Annotation annotation=tClass.getAnnotation(LightTable.class);
        if(annotation !=null){
            return ((LightTable)annotation).name();
        }
        String[] ps = tClass.getName().split("\\.");
        String name = ps[ps.length-1];
        StringBuffer classname = new StringBuffer();
        classname.append(String.valueOf(name.charAt(0)).toLowerCase());
        classname.append(name.substring(1));
        return parseFieldName(classname.toString());
    }

    /**
     * 将表字段转换成实体对应的属性 如:user_id 转换结果为 userId
     * @param tableFieldName
     * @return
     */
    public static String parseEntityField(String tableFieldName){
        StringBuffer stringBuffer = new StringBuffer();
        String[] arrs = tableFieldName.split("_");
        boolean isu = false;
        for (String str : arrs){
            if(!isu){
                stringBuffer.append(str.toLowerCase());
                isu = true;
            }
            else{
                stringBuffer.append(str.substring(0,1).toUpperCase());
                stringBuffer.append(str.substring(1,str.length()).toLowerCase());
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 将实体属性转换成对应的数据库字段名 如:userId == user_id
     * @param name
     * @return
     */
    public static String parseFieldName(String name){
        StringBuffer stringBuffer =new StringBuffer();
        boolean isok=false;
        for(int i=0;i<name.length();i++){
            char c = name.charAt(i);
            if(c>64 && c<91 && !isok){

                stringBuffer.append("_"+String.valueOf(c).toLowerCase());
                isok = true;
            }
            else {
                stringBuffer.append(String.valueOf(c));
                if(isok) isok=false;
            }
        }
        return stringBuffer.toString().toLowerCase();
    }

    /**
     * 根据实体生成如 (null,#{userName},#{email})
     * @param tClass
     * @param primaryKey
     * @return
     * @throws Exception
     */
    public static String parseFieldParam(Class tClass,String primaryKey) throws Exception{
        StringBuffer stringBuffer = new StringBuffer();
        Field[] datalist = tClass.getDeclaredFields();
        for(Field field : datalist){
            if(field.getName().equals(primaryKey)){
                stringBuffer.append("null");
            }
            else{
                stringBuffer.append(String.format("#{%s}", field.getName()));
            }
            stringBuffer.append(",");
        }
        return stringBuffer.deleteCharAt(stringBuffer.length()-1).toString();
    }
    /**
     *
     * @param fieldList
     * @param tClass
     * @return
     * @throws Exception
     */
    public static String parseFieldList(List<String> fieldList,Class tClass,boolean isInsert,String asTable) throws Exception{
        StringBuffer stringBuffer = new StringBuffer();
        String formatStr = asTable+".`%s` as %s";
        if(isInsert){
            formatStr ="`%s`";
        }
        if(fieldList!=null && !fieldList.isEmpty()){
           for (String colname : fieldList){
               stringBuffer.append(colname);
               stringBuffer.append(",");
           }
           return stringBuffer.deleteCharAt(stringBuffer.length()-1).toString();
        }
        Field[] datalist = tClass.getDeclaredFields();
        for(Field field : datalist){
            LightColumn  lightColumn = field.getAnnotation(LightColumn.class);
            if(null == lightColumn){
                stringBuffer.append(String.format(formatStr,parseFieldName(field.getName()),field.getName()));
            }
            else{
                stringBuffer.append(String.format(formatStr,lightColumn.value(),field.getName()));
            }
            stringBuffer.append(",");
        }
        return stringBuffer.deleteCharAt(stringBuffer.length()-1).toString();
    }

    /**
     *为Insert 插入语句生成if 条件字段判断语句,字段为null或空将不出现在insert into语句内
     * @param tClass
     * @return
     * @throws Exception
     */
    public static Map<String,SqlNode> parseFieldSqlNodeList(Class tClass) throws Exception{

        Map<String,SqlNode> result = new HashMap<>();
        List<SqlNode> ifSqlNode = new ArrayList<>();
        List<SqlNode> ifSqlNodeValues = new ArrayList<>();
        String formatStr ="`%s`,";
        String formatValues ="#{%s},";

        Field[] datalist = tClass.getDeclaredFields();
        String conditionVal = null;
        for(Field field : datalist){
            LightColumn  lightColumn = field.getAnnotation(LightColumn.class);
            conditionVal = field.getType().equals(String.class)?"%s!=null && %s!=''":"%s!=null";

            if(null == lightColumn){
                ifSqlNode.add(new IfSqlNode(new StaticTextSqlNode(String.format(formatStr,parseFieldName(field.getName()))),String.format(conditionVal,field.getName(),field.getName())));
                ifSqlNodeValues.add(new IfSqlNode(new StaticTextSqlNode(String.format(formatValues,field.getName())),String.format(conditionVal,field.getName(),field.getName())));
            }
            else{
                ifSqlNode.add(new IfSqlNode(new StaticTextSqlNode(String.format(formatStr,lightColumn.value())),String.format(conditionVal,field.getName(),field.getName())));
                ifSqlNodeValues.add(new IfSqlNode(new StaticTextSqlNode(String.format(formatValues,lightColumn.value())),String.format(conditionVal,field.getName(),field.getName())));
            }
        }
        result.put(INSERT_COLUMNS_LIST,new MixedSqlNode(ifSqlNode));
        result.put(INSERT_COLUMNS_VALUES,new MixedSqlNode(ifSqlNodeValues));
        return result;
    }

    public static void main(String[] args) throws Exception{
        System.out.println(BuildUtil.parseEntityField("uid_id_dddd_fffff"));
    }


}
