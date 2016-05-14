package com.clown.lightdb.plugins;

import com.clown.lightdb.Pagination;
import com.clown.lightdb.core.PageBounds;
import com.clown.lightdb.utils.ExecutorUtil;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.result.DefaultResultHandler;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by len.li on 25/3/2016.
 * mybatis 分页插件
 */
@Intercepts({@Signature(
        type= Executor.class,
        method = "query",
        args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})})
public class PaginationPlugin implements Interceptor {

    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    static int ROWBOUNDS_INDEX = 2;
    static int RESULT_HANDLER_INDEX = 3;
    static ExecutorService Pool;
    boolean asyncTotalCount = false;



    @Override
    public Object intercept(final Invocation invocation) throws Throwable {

        final Executor executor = (Executor) invocation.getTarget();
        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement mappedStatement = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];

        final Object parameter = queryArgs[PARAMETER_INDEX];
        final RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX];

        if(mappedStatement.getSqlCommandType()!= SqlCommandType.SELECT){
            return invocation.proceed();
        }

        PageBounds pageBounds = null;
        if(parameter instanceof Map){
            Map<String,Object> pas = (Map) parameter;
            for(String key : pas.keySet()){
                if(pas.get(key) instanceof PageBounds){
                    pageBounds = (PageBounds) pas.get(key);
                    break;
                }
            }
        }
        else{
             pageBounds = (PageBounds) queryArgs[PARAMETER_INDEX];
        }



        if(pageBounds == null || (pageBounds.getOffset() == RowBounds.NO_ROW_OFFSET && pageBounds.getLimit() == RowBounds.NO_ROW_LIMIT)){
            return invocation.proceed();
        }
         else {

            int offset = pageBounds.getOffset()<=0?1:pageBounds.getOffset();
            int limit = pageBounds.getLimit()<=0?15:pageBounds.getLimit();

            final BoundSql boundSql = mappedStatement.getBoundSql(parameter);

            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET,RowBounds.NO_ROW_LIMIT);

            final BoundSql newBoundSql = copyBoundSql(mappedStatement.getConfiguration(),boundSql,(offset-1)*limit,limit);

            queryArgs[MAPPED_STATEMENT_INDEX] = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
            final MappedStatement copyMappedStatement= (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];



            // 获得数据列表 只对实体类测试过，map没有测试
            Callable<List> listCallable = new Callable<List>() {
                @Override
                public List call() throws Exception {
                    DefaultResultHandler defaultResultHandler = new DefaultResultHandler();
                    PreparedStatement countStmt = executor.getTransaction().getConnection().prepareStatement(newBoundSql.getSql());
                    PreparedStatementHandler preparedStatementHandler = new PreparedStatementHandler(executor,copyMappedStatement,parameter,new RowBounds(),null,newBoundSql);

                    // 解析SQL参数
                    DefaultParameterHandler handler = new DefaultParameterHandler(copyMappedStatement,parameter,newBoundSql);
                    handler.setParameters(countStmt);

                    List result = preparedStatementHandler.query(countStmt,null);
                    if(result.size()<=0){
                        return  defaultResultHandler.getResultList();
                    }
                    return result;
                }
            };


            /**
             * 获得SQL 总数据记录
             */
            Callable<Integer> countTask = new Callable() {
                public Object call() throws Exception {
                    Integer count;
                    Cache cache = mappedStatement.getCache();
                    if(cache != null && mappedStatement.isUseCache() && mappedStatement.getConfiguration().isCacheEnabled()){
                        CacheKey cacheKey = executor.createCacheKey(mappedStatement,parameter,null,copyCountBoundSql(mappedStatement,boundSql));
                        count = (Integer)cache.getObject(cacheKey);
                        if(count == null){
                            count = ExecutorUtil.getCount(mappedStatement,executor.getTransaction(),parameter,copyCountBoundSql(mappedStatement,boundSql));
                            cache.putObject(cacheKey, count);
                        }
                    }else{
                        count = ExecutorUtil.getCount(mappedStatement,executor.getTransaction(),parameter,copyCountBoundSql(mappedStatement,boundSql));
                    }

                    return count;
                }
            };

            Future<Integer> countFutrue = call(countTask, asyncTotalCount);
            Future<List> listFuture = call(listCallable,asyncTotalCount);
            Pagination pagination = new Pagination();
            pagination.setTotal(countFutrue.get());
            pagination.setCurrentPage(offset);
            pagination.setPageSize(limit);
            pagination.setList(listFuture.get());
            List data = new ArrayList();
            data.add(pagination);
            return data;
        }
    }

    private <T> Future<T> call(Callable callable, boolean async){
        if(async){
            return Pool.submit(callable);
        }else{
            FutureTask<T> future = new FutureTask(callable);
            future.run();
            return future;
        }
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }



    private MappedStatement copyFromMappedStatement(MappedStatement ms,SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(),ms.getId(),newSqlSource,ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if(ms.getKeyProperties() != null && ms.getKeyProperties().length !=0){
            StringBuffer keyProperties = new StringBuffer();
            for(String keyProperty : ms.getKeyProperties()){
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length()-1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }

        //setStatementTimeout()
        builder.timeout(ms.getTimeout());

        //setStatementResultMap()
        builder.parameterMap(ms.getParameterMap());

        //setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ResultSetType.FORWARD_ONLY);

        //setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    /**
     * 生成新的BoundSql,只适合Mysql查询语句,不支持groudby语句
     * @param configuration
     * @param boundSql
     * @param offset
     * @param limit
     * @return
     */
    public BoundSql copyBoundSql(Configuration configuration,BoundSql boundSql, int offset, int limit){

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(boundSql.getSql().replace(";",""));
        stringBuffer.append(String.format(" limit %s,%s;",offset,limit));
        return new BoundSql(configuration,stringBuffer.toString(),
                boundSql.getParameterMappings(), boundSql.getParameterObject());
    }

    /**
     * 查询总数量的SQL Boundsql
     * @param mappedStatement
     * @param boundSql
     * @return
     */
    public BoundSql copyCountBoundSql(MappedStatement mappedStatement,BoundSql boundSql){
        StringBuffer stringBuffer = new StringBuffer("SELECT COUNT(*) FROM (");
        stringBuffer.append(boundSql.getSql().replace(";",""));
        stringBuffer.append(") as t;");
        return new BoundSql(mappedStatement.getConfiguration(),stringBuffer.toString(),
                boundSql.getParameterMappings(), boundSql.getParameterObject());
    }

    /**
     * 定义数据源
     */
    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;
        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

}
