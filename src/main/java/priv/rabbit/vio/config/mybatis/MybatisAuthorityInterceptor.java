package priv.rabbit.vio.config.mybatis;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import priv.rabbit.vio.config.annotation.SQLPermission;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisAuthorityInterceptor implements Interceptor {

    private static final String SUFFIX_COUNT = "_COUNT";

    private static final Logger logger = LoggerFactory.getLogger(MybatisAuthorityInterceptor.class);

    private static final Map<String, SQLPermission> SQL_PERMISSION_MAPPER = new HashMap();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        //通过MetaObject优雅访问对象的属性，这里是访问statementHandler的属性;：MetaObject是Mybatis提供的一个用于方便、
        //优雅访问对象属性的对象，通过它可以简化代码、不需要try/catch各种reflect异常，同时它支持对JavaBean、Collection、Map三种类型对象的操作。
        MetaObject metaObject = MetaObject
                .forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                        new DefaultReflectorFactory());
        //先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        //Class.forName(id, false, Thread.currentThread().getContextClassLoader()).getInterfaces();

        //sql语句类型 select、delete、insert、update
        if (SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            BoundSql boundSql = statementHandler.getBoundSql();
            //入参对象
            Object param = boundSql.getParameterObject();
            logger.info("入参 param : {}", JSON.toJSON(param));
            //id为执行的mapper方法的全路径名，如com.uv.dao.UserMapper.insertUser
            SQLPermission sqlPermission = getSqlPermission(mappedStatement.getId());
            if (null != sqlPermission) {
                logger.info("注解内容 : {}", sqlPermission.menuId());
                //获取到原始sql语句
                String sql = boundSql.getSql();
                sql = "select * from (" + sql + ") t";
                logger.info("sql : {} ", sql);
                //通过反射修改sql语句
                Field field = boundSql.getClass().getDeclaredField("sql");
                field.setAccessible(true);
                field.set(boundSql, sql);
            }
        }
        //数据库连接信息
        /*Configuration configuration = mappedStatement.getConfiguration();
        ComboPooledDataSource dataSource = (ComboPooledDataSource)configuration.getEnvironment().getDataSource();
        dataSource.getJdbcUrl();*/
        return invocation.proceed();
    }

    /**
     * 获取注解
     *
     * @param id 为执行的mapper方法的全路径名，如com.dao.UserMapper.insertUser
     * @return
     */
    private SQLPermission getSqlPermission(String id) {
        SQLPermission sqlPermission = SQL_PERMISSION_MAPPER.computeIfAbsent(id, key -> {
            logger.info("key: {}", key);
            int lastIndex = key.lastIndexOf(".");
            String interfaceName = key.substring(0, lastIndex);
            String methodName = key.substring(lastIndex + 1);
            Class cls = null;
            try {
                cls = Class.forName(interfaceName, false, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException e) {
                logger.error("获取mapper 接口异常 ", e);
                e.printStackTrace();
            }
            //Method method = ReflectionUtils.findMethod(cls, methodName);
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                if (null != method && method.getName().equals(methodName)) {
                    return method.getAnnotation(SQLPermission.class);
                }
            }
            return null;
        });
        if (sqlPermission == null && id.endsWith(SUFFIX_COUNT)) {
            sqlPermission = getSqlPermission(id.replace(SUFFIX_COUNT, ""));
        }
        return sqlPermission;
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof StatementHandler ? Plugin.wrap(target, this) : target;
    }

    @Override
    public void setProperties(Properties properties) {
        //此处可以接收到配置文件的property参数
        System.out.println("setProperties==" + properties.getProperty("name"));
    }

}
