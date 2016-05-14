package com.clown.lightdb.utils;

import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Created by len.li on 21/3/2016.
 */
public class SqlSessionFactoryUtil {

    private static SqlSessionFactoryUtil sqlSessionFactoryUtil;

    private SqlSessionFactory sqlSessionFactory;

    public void init(){
        sqlSessionFactoryUtil = this;
        sqlSessionFactoryUtil.sqlSessionFactory = this.sqlSessionFactory;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public static SqlSessionFactory findSqlSessionFactory(){
        return sqlSessionFactoryUtil.getSqlSessionFactory();
    }
}
