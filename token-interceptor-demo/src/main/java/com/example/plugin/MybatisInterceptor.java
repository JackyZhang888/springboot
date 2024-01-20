package com.example.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Properties;

@Component
//拦截StatementHandler类中参数类型为Statement的prepare方法（prepare=在预编译SQL前加入修改的逻辑）
//即拦截 Statement prepare(Connection var1, Integer var2) 方法
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
@Slf4j
public class MybatisInterceptor implements Interceptor  {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取原始sql
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        // 通过MetaObject优雅访问对象的属性，这里是访问statementHandler的属性;：MetaObject是Mybatis提供的一个用于方便、
        // 优雅访问对象属性的对象，通过它可以简化代码、不需要try/catch各种reflect异常，同时它支持对JavaBean、Collection、Map三种类型对象的操作。
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,new DefaultReflectorFactory());
        // 先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // 通过反射，拦截并修改sql
        String mSql = sqlAnnotationEnhance(mappedStatement, boundSql);

        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, mSql);

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
    }


    /**
     * 通过反射，拦截方法上带有自定义@InterceptAnnotation注解的方法，并增强sql
     * @param id 方法全路径
     * @param sqlCommandType sql类型
     * @param sql 所执行的sql语句
     */
    private String sqlAnnotationEnhance(MappedStatement mappedStatement, BoundSql boundSql) throws ClassNotFoundException {
        // 获取到原始sql语句
        String sql = boundSql.getSql().toLowerCase();
        // sql语句类型 select、delete、insert、update
        String sqlCommandType = mappedStatement.getSqlCommandType().toString();

        // id为执行的mapper方法的全路径名，如com.cq.UserMapper.insertUser， 便于后续使用反射
        String id = mappedStatement.getId();
        // 获取当前所拦截的方法名称
        String mName = id.substring(id.lastIndexOf(".") + 1);
        // 通过类全路径获取Class对象
        Class<?> classType = Class.forName(id.substring(0, id.lastIndexOf(".")));

        // 获得参数集合
        String paramString = null;
        if (boundSql.getParameterObject() != null) {
            paramString = boundSql.getParameterObject().toString();
        }

        // 遍历类中所有方法名称，并匹配上当前所拦截的方法
        for (Method method : classType.getDeclaredMethods()) {
            if (mName.equals(method.getName())) {
                log.info("intercept func:{}, type:{}, origin SQL：{}", mName, sqlCommandType, sql);
                // change sql here
                return sql;
            }
        }
        return sql;
    }
}
