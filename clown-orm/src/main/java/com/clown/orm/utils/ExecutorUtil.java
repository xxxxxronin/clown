package com.clown.orm.utils;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.transaction.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by len.li on 2016/4/12.
 */
public class ExecutorUtil {



    public static int getCount(
            final MappedStatement mappedStatement, final Transaction transaction, final Object parameterObject,
            final BoundSql boundSql) throws SQLException {
        final String count_sql =boundSql.getSql();

        Connection connection = transaction.getConnection();
        PreparedStatement countStmt = connection.prepareStatement(count_sql);

        DefaultParameterHandler handler = new DefaultParameterHandler(mappedStatement,parameterObject,boundSql);

        handler.setParameters(countStmt);


        ResultSet rs = countStmt.executeQuery();

        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        return count;

    }

    /**
     * 生成查询总数据的SQL语句
     * @param boundSql
     * @return
     */
    public static String countSql(BoundSql boundSql){
        StringBuffer stringBuffer = new StringBuffer("SELECT COUNT(*) FROM (");
        stringBuffer.append(boundSql.getSql());
        stringBuffer.append(") as t");
        return stringBuffer.toString();
    }
}
