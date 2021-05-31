package priv.rabbit.vio.config.mybatis;

import com.alibaba.fastjson.JSON;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.util.CollectionUtils;
import priv.rabbit.vio.config.annotation.SQLPermission;
import priv.rabbit.vio.dto.DataPermission;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisAuthorityInterceptor implements Interceptor {

    public static final ThreadLocal<DataPermission> LOCAL_DATA_PERMISSION = new ThreadLocal();

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

        //sql语句类型 select、delete、insert、update
        if (SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            BoundSql boundSql = statementHandler.getBoundSql();
            //入参对象
            Object param = boundSql.getParameterObject();
            logger.info("入参 param : {}", JSON.toJSON(param));
            //id为执行的mapper方法的全路径名，如com.uv.dao.UserMapper.insertUser
            //SQLPermission sqlPermission = getSqlPermission(mappedStatement.getId());
            //获取到原始sql语句
            String sql = handlerSql(boundSql.getSql());
            logger.info("sql : {} ", sql);

            //通过反射修改sql语句
            Field field = boundSql.getClass().getDeclaredField("sql");
            field.setAccessible(true);
            field.set(boundSql, sql);
        }
        //数据库连接信息
        /*Configuration configuration = mappedStatement.getConfiguration();
        ComboPooledDataSource dataSource = (ComboPooledDataSource)configuration.getEnvironment().getDataSource();
        dataSource.getJdbcUrl();*/
        return invocation.proceed();
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

    public String handlerSql(String sql) {
        //jsqlparser 解析 sql
        Statement stmt = null;
        try {
            stmt = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;
        DataPermission dataPermission = LOCAL_DATA_PERMISSION.get();
        if (StringUtils.isNotBlank(dataPermission.getAlias())) {
            setWhereHaveAlias(plainSelect);
        } else if (!fromItem(plainSelect.getFromItem())) {
            setWhere(plainSelect);
        }
        return select.toString();
    }


    public static void setWhereHaveAlias(PlainSelect pls) {
        DataPermission dataPermission = LOCAL_DATA_PERMISSION.get();
        FromItem fromItem = pls.getFromItem();
        String aliasName = Optional.ofNullable(fromItem).map(FromItem::getAlias).map(Alias::getName).orElse("");
        if (aliasName.equals(dataPermission.getAlias())) {
            extracted(dataPermission, pls);
            return;
        }
        if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (null != subSelect.getSelectBody()) {
                PlainSelect plainSelect = (PlainSelect) subSelect.getSelectBody();
                aliasName = Optional.ofNullable(plainSelect).map(PlainSelect::getFromItem).map(FromItem::getAlias).map(Alias::getName).orElse("");
                if (aliasName.equals(dataPermission.getAlias())) {
                    extracted(dataPermission, plainSelect);
                    return;
                }
                if (!CollectionUtils.isEmpty(plainSelect.getJoins())) {
                    Optional<Join> join = plainSelect.getJoins().stream().filter(var -> Optional.ofNullable(var).map(Join::getRightItem)
                            .map(FromItem::getAlias).map(Alias::getName).orElse("").equals(dataPermission.getAlias())).findFirst();
                    if (join.isPresent()) {
                        extracted(dataPermission, plainSelect);
                        return;
                    }

                }

            }
        }
    }

    private static void extracted(DataPermission dataPermission, PlainSelect plainSelect) {
        Expression where = null;
        try {
            String whereSql = plainSelect.getWhere() != null ?
                    plainSelect.getWhere().toString() + " AND " + dataPermission.getAlias() + "." + dataPermission.getWhereSql()
                    : dataPermission.getAlias() + "." + dataPermission.getWhereSql();
            where = CCJSqlParserUtil.parseCondExpression(whereSql);
            plainSelect.setWhere(where);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }

    public boolean fromItem(FromItem fromItem) {
        if (fromItem instanceof Table) {
            return false;
        }
        if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (null != subSelect.getSelectBody()) {
                PlainSelect plainSelect = (PlainSelect) subSelect.getSelectBody();
                if (!fromItem(plainSelect.getFromItem())) {
                    setWhere(plainSelect);
                    return true;
                }
            }
        }
        return true;
    }

    private void setWhere(PlainSelect plainSelect) {
        StringBuilder stringBuilder = new StringBuilder(" ");
        if (null != plainSelect.getWhere()) {
            stringBuilder.append(plainSelect.getWhere().toString());
            logger.info(" getWhere : " + plainSelect.getWhere().toString());
        }
        try {
            DataPermission dataPermission = LOCAL_DATA_PERMISSION.get();
            logger.info("数据权限 ： {}", dataPermission.getWhereSql());
            stringBuilder.append(dataPermission.getWhereSql());
            if (StringUtils.isNotBlank(stringBuilder.toString())) {
                Expression where = CCJSqlParserUtil.parseCondExpression(stringBuilder.toString());
                plainSelect.setWhere(where);
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }
}
