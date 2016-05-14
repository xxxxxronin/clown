package com.clown.lightdb;

import com.clown.lightdb.core.Condition;
import com.clown.lightdb.core.Join;
import com.clown.lightdb.core.MapperConfig;
import com.clown.lightdb.core.MySQLBuildNode;
import com.clown.lightdb.utils.SqlSessionFactoryUtil;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.*;


/**
 * Created by len.li on 17/3/2016.
 *
 */
public final class Mapper extends SqlSessionDaoSupport {


    private  SqlSessionFactory sqlSessionFactory =null;

    private MapperConfig mapperConfig;

    private static Mapper getInstance(Class tableModel){
        return getInstance(MapperConfig.SELECT,tableModel, MapperConfig.DEFAULT_PRIMARY_KEY);
    }

    /**
     * 此处要进行单例优化，目前没有做，后续更改，不影响后续使用
     * @param queryType
     * @param tableModel
     * @return
     */
    private static Mapper getInstance(String queryType, Class tableModel){
        return getInstance(queryType,tableModel, MapperConfig.DEFAULT_PRIMARY_KEY);
    }


    private static Mapper getInstance(String queryType, Class tableModel, String pk){
        Mapper mapper = new Mapper();
        mapper.mapperConfig = new MapperConfig();
        mapper.getMapperConfig().setTarget(queryType,tableModel);
        mapper.getMapperConfig().setPrimaryKey(pk);
        return mapper;
    }

    private Mapper(){
        this.sqlSessionFactory = SqlSessionFactoryUtil.findSqlSessionFactory();
        setSqlSessionFactory(getSqlSessionFactory());
    }



    /**
     *Select 查询
     * @return
     * @throws Exception
     */
    public static Mapper select(Class target) throws Exception{
       return getInstance(target);
    }

    /**
     *
     * @param tableModel
     * @return
     * @throws Exception
     */
    public static Mapper delete(Class tableModel) throws Exception{
       return getInstance(MapperConfig.DELETE,tableModel);
    }

    /**
     *
     * @param model
     * @return
     * @throws Exception
     */
    public static Mapper update(Object model) throws Exception{

        return getInstance(MapperConfig.UPDATE,model.getClass()).setParameterModel(model);
    }

    /**
     * 插入一条记录
     * @param model
     * @return
     * @throws Exception
     */
    public static Mapper insert(Object model) throws Exception{
       return insert(model, MapperConfig.DEFAULT_PRIMARY_KEY);
    }

    public static Mapper insert(Object model, String pk) throws Exception{
        return getInstance(MapperConfig.INSERT,model.getClass(),pk).setParameterModel(model);
    }

    /**
     * 设置表实体参数
     * @param model
     * @return
     */
    protected Mapper setParameterModel(Object model){
        this.mapperConfig.setParameterModel(model);
        return this;
    }


    /**
     *  设置查询字段列
     * @param fieldName
     * @return
     */
    public Mapper fields(String... fieldName){
        this.mapperConfig.addFields(fieldName);
        return this;
    }
    /**
     * 添加SQL条件语句
     * @param conditions
     * @return
     */
    public Mapper where(Condition... conditions){
        this.mapperConfig.addCondition(conditions);
        return this;
    }

    public  int exec() throws Exception{
        int result = 0;

        if(mapperConfig.getQueryType().equals(MapperConfig.INSERT)){
            String statementName = this.buildStatement(SqlCommandType.INSERT,null,mapperConfig.getParameterModel().getClass(), MapperConfig.INSERT);
            result= getSqlSession().insert(statementName,mapperConfig.getParameterModel());
        }

        if(mapperConfig.getQueryType().equals(MapperConfig.UPDATE)){
            String statementName = this.buildStatement(SqlCommandType.UPDATE,null,mapperConfig.getParameterModel().getClass(), MapperConfig.UPDATE);
            Map<String,Object> param = mapperConfig.getConditionParameter();
            param.put("_param",mapperConfig.getParameterModel());
            result= getSqlSession().update(statementName,param);
        }

        if(mapperConfig.getQueryType().equals(MapperConfig.DELETE)){
            String statementName = this.buildStatement(SqlCommandType.DELETE,null,null, MapperConfig.DELETE);
            result= getSqlSession().update(statementName,mapperConfig.getConditionParameter());
        }


        return  result;
    }

    /**
     * 查询一条匹配记录
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T findOne(Class<T> t) throws Exception{
        if(mapperConfig.getQueryType().equals(MapperConfig.SELECT)){
            getMapperConfig().addLimit(1,1);
            String statementName = this.buildStatement(SqlCommandType.SELECT,t,null);
            return getSqlSession().selectOne(statementName,mapperConfig.getConditionParameter());
        }
        return null;
    }

    /**
     * 查询集合列表
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> List<T> findList(Class<T> t) throws Exception{
        if(mapperConfig.getQueryType().equals(MapperConfig.SELECT)){
            String statementName = this.buildStatement(SqlCommandType.SELECT,t,null);
            return getSqlSession().selectList(statementName,mapperConfig.getConditionParameter());
        }
        return null;
    }

    /**
     * 获得查询总数
     * @return
     * @throws Exception
     */
    public int count() throws Exception{
        if(mapperConfig.getQueryType().equals(MapperConfig.SELECT)){
            String statementName = this.buildStatement(SqlCommandType.SELECT,Integer.class,null, MapperConfig.SELECT_ALL);
            return getSqlSession().selectOne(statementName,mapperConfig.getConditionParameter());
        }
        return 0;
    }

    public <T> Pagination<T> findPages(Class<T> t, int currentPage, int pageSize) throws Exception{
        if(mapperConfig.getQueryType().equals(MapperConfig.SELECT)){
            Pagination<T> pagination = new Pagination<T>();
            getMapperConfig().addLimit(currentPage,pageSize);
            pagination.setTotal(this.count());
            if(pagination.getTotal()>0){
                pagination.setList(this.findList(t));
            }
            return pagination;
        }
        return null;
    }



    /**
     * 左连接查询,建议在三个以上的表连接查询，三个以上请自定义Mapper XML
     * @param joins
     * @return
     */
    public Mapper join(Join...joins){
        this.mapperConfig.addJoin(joins);
        return this;
    }

    public Mapper orderASC(String fieldName){
        getMapperConfig().addOrderBy(fieldName, MapperConfig.ORDER_ASC);
        return this;
    }

    public Mapper orderDESC(String fieldName){
        getMapperConfig().addOrderBy(fieldName, MapperConfig.ORDER_DESC);
        return this;
    }

    public Mapper limit(int currentPage, int pageSize){
        getMapperConfig().addLimit(currentPage,pageSize);
        return this;
    }


    protected String buildStatement(SqlCommandType queryType,Class resultType,Class paramType) throws Exception{
      return this.buildStatement(queryType,resultType,paramType,"list");
    }

    protected String buildStatement(SqlCommandType queryType,Class resultType,Class paramType,String endFix) throws Exception{
        String statementName = this.getMapperConfig().generateStatementName(paramType,resultType,endFix);
        logger.info("statementName:"+statementName);
        if(!hasMappedStatement(statementName)){
            long startTime = System.currentTimeMillis();
            MySQLBuildNode mySQLBuildNode = new MySQLBuildNode(mapperConfig,getSqlSession());
            SqlNode rootNode = null;
            if(endFix.equals(MapperConfig.SELECT_ALL)){
                rootNode = mySQLBuildNode.buildCountNode();
            }
            else {
                rootNode = mySQLBuildNode.buildNormalNode();
            }
            DynamicSqlSource dynamicSqlSource = new DynamicSqlSource(getSqlSession().getConfiguration(),rootNode);

            SqlCommandType sqlCommandType = SqlCommandType.valueOf(queryType.toString());
            createMappedStatement(statementName,dynamicSqlSource,sqlCommandType,resultType,paramType);
        }
        return statementName;
    }

    /**
     * 生成key statement
     * @param statementName
     * @return
     * @throws Exception
     */
    protected MappedStatement buildKeyMappedStatement(String statementName,String keyProperty){
        StringBuffer stringBuffer = new StringBuffer(statementName);
        stringBuffer.append("-keystatement");
        SqlNode rootNode = new StaticTextSqlNode("SELECT LAST_INSERT_ID();");
        DynamicSqlSource dynamicSqlSource = new DynamicSqlSource(getSqlSession().getConfiguration(),rootNode);
        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(getSqlSession().getConfiguration(),stringBuffer.toString(),dynamicSqlSource,SqlCommandType.UNKNOWN);

        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        ResultMap.Builder inlineResultMapBuilder = new ResultMap.Builder(
                getSqlSession().getConfiguration(),
                statementBuilder.id(),
                Integer.class,
                new ArrayList<ResultMapping>(),
                null);
        resultMaps.add(inlineResultMapBuilder.build());
        statementBuilder.resultMaps(resultMaps);
        statementBuilder.keyProperty(keyProperty);
        return statementBuilder.build();


    }

    protected SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    /**
     * 是否己存在Statement
     * @param statementName
     * @return
     */
    private boolean hasMappedStatement(String statementName){
        Collection<String> stringCollection = getSqlSession().getConfiguration().getMappedStatementNames();
        return stringCollection.contains(statementName);
    }

    /**
     * 创建Statement
     * @param statementName
     * @param sqlSource
     * @param sqlCommandType
     * @param resultClass
     * @param parameterClass
     */
    private void createMappedStatement(String statementName,
                                      SqlSource sqlSource, SqlCommandType sqlCommandType, Class resultClass, Class parameterClass){
        MappedStatement statement=null;
        if(null == parameterClass){
            parameterClass = HashMap.class;
        }
        getSqlSession().getConfiguration().setUseGeneratedKeys(true);
        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(getSqlSession().getConfiguration(),statementName,sqlSource,sqlCommandType);
        ParameterMap.Builder parameterMap =new  ParameterMap.Builder(
                getSqlSession().getConfiguration(),
                statementBuilder.id(),
                parameterClass,
                new ArrayList<ParameterMapping>());


        if(sqlCommandType == SqlCommandType.valueOf(SqlCommandType.SELECT.toString())){
            List<ResultMap> resultMaps = new ArrayList<ResultMap>();
            ResultMap.Builder inlineResultMapBuilder = new ResultMap.Builder(
                    getSqlSession().getConfiguration(),
                    statementBuilder.id(),
                    resultClass,
                    new ArrayList<ResultMapping>(),
                    null);
            resultMaps.add(inlineResultMapBuilder.build());
            statementBuilder.resultMaps(resultMaps);
        }
        statementBuilder.parameterMap(parameterMap.build());
        statement = statementBuilder.build();

        if(sqlCommandType == SqlCommandType.INSERT){
            statementBuilder.keyGenerator(new SelectKeyGenerator(buildKeyMappedStatement(statementName,"id"),false));
            statement = statementBuilder.build();
        }
        getSqlSession().getConfiguration().addMappedStatement(statement);
    }


//    public void checkSqlSession() throws Exception{
//
//    }

    public MapperConfig getMapperConfig() {
        return mapperConfig;
    }
}
