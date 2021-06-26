package com.happy.video.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

// ExamplePlugin.java
@Intercepts({@Signature(
        // MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class ExamplePlugin implements Interceptor {
    private Properties properties = new Properties();

    public Object intercept(Invocation invocation) throws Throwable {
        // implement pre processing if need
        Object returnObject = invocation.proceed();
        // implement post processing if need


        System.out.println("官网插件demo执行【query - appedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler】");


        return returnObject;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}