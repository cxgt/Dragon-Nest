package com.learningRoad.Interceptor;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mysql.cj.MysqlConnection;
import com.mysql.cj.Query;
import com.mysql.cj.interceptors.QueryInterceptor;
import com.mysql.cj.log.Log;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ServerSession;

import java.util.Properties;
import java.util.function.Supplier;

/**
 * @author chenxin
 * @date 2023/09/04 09:55
 */
public class JdbcInterceptor implements QueryInterceptor {
    @Override
    public QueryInterceptor init(MysqlConnection conn, Properties props, Log log) {
        return this;
    }

    @Override
    public <T extends Resultset> T preProcess(Supplier<String> sql, Query interceptedQuery) {
        System.out.println("About to execute the following SQL: " + sql.get());
        System.out.println(sql.get());
        return null;
    }

    @Override
    public boolean executeTopLevelOnly() {
        return true;
    }

    @Override
    public void destroy() {
    }

    @Override
    public <T extends Resultset> T postProcess(Supplier<String> sql, Query interceptedQuery, T originalResultSet, ServerSession serverSession) {
        if (originalResultSet != null) {
            System.out.println("Query resulted in " + originalResultSet.getRows().size() + " rows");
            ColumnDefinition columnDefinition = originalResultSet.getColumnDefinition();
            System.out.println("columnDefinition message"+ JSONObject.toJSONString(columnDefinition.getFields()));
            System.out.println(sql.get());
        }
        return originalResultSet;
    }
}
