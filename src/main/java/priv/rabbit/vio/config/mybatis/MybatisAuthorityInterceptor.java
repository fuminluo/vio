package priv.rabbit.vio.config.mybatis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
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
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisAuthorityInterceptor implements Interceptor {

    protected static final ThreadLocal<SqlHelperDTO> LOCAL_DATA = new ThreadLocal();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (LOCAL_DATA.get() == null || StringUtils.isBlank(LOCAL_DATA.get().getWhereSql())) {
            return invocation.proceed();
        }
        if (!LOCAL_DATA.get().getIsLast()) {
            if (StringUtils.isBlank(LOCAL_DATA.get().getAlias()) && StringUtils.isBlank(LOCAL_DATA.get().getTableName())) {
                return invocation.proceed();
            }
        }
        try {
            StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
            MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            //只对查询做处理
            if (SqlCommandType.SELECT == mappedStatement.getSqlCommandType() && StatementType.CALLABLE != mappedStatement.getStatementType()) {
                BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
                String sql = handlerSql(boundSql.getSql());
                SystemMetaObject.forObject(boundSql).setValue("sql", sql);
            }
            return invocation.proceed();
        } catch (Exception e) {
            log.error("DataPopeDomInterceptor 拦截器发生错误 {} ", e);
        } finally {
            //清除线程数据
            if (null != LOCAL_DATA.get() && (LOCAL_DATA.get().getAllowCount() == 1
                    || LOCAL_DATA.get().getExistCount().get() >= LOCAL_DATA.get().getAllowCount())) {
                LOCAL_DATA.remove();
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    /**
     * 处理 sql
     *
     * @param sql
     * @return
     */
    private String handlerSql(String sql) {
        //直接拼接
        if (LOCAL_DATA.get().getIsLast()) {
            return sql + LOCAL_DATA.get().getWhereSql();
        }
        //jsqlparser 解析 sql
        Statement stmt = null;
        try {
            stmt = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            log.error("jsqlparser 解析 sql 异常 {}", e);
            LOCAL_DATA.get().getExistCount().addAndGet(1);
            return sql;
        }

        //select SelectItem from FromItem where Expression
        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();
        analysisSelectBody(selectBody);
        analysisWithItemList(select.getWithItemsList());
        return select.toString();
    }

    private void analysisSelectBody(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            PlainSelect plainSelect = (PlainSelect) selectBody;
            analysisFromItem(plainSelect.getFromItem(), plainSelect);
            analysisJoin(plainSelect.getJoins(), plainSelect);
            //analysisTables(plainSelect.getIntoTables(), plainSelect);
        }
        if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            analysisSelectBody(withItem.getSelectBody());
        }
    }

    private void analysisWithItemList(List<WithItem> withItemList) {
        if (!CollectionUtils.isEmpty(withItemList)) {
            for (WithItem withItem : withItemList) {
                analysisSelectBody(withItem.getSelectBody());
            }
        }

    }


    private void analysisFromItem(FromItem fromItem, PlainSelect plainSelect) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            analysisSubJoin(subJoin, plainSelect);
            analysisFromItem(subJoin.getLeft(), plainSelect);
            //analysisJoin(subJoin.getJoinList(), plainSelect);
            analysisJoin(Arrays.asList(subJoin.getJoin()), plainSelect);
        }
        if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            analysisSubSelect(subSelect);
        }
        if (fromItem instanceof Table) {
            analysisTable((Table) fromItem, plainSelect);
        }
        if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                analysisSelectBody(subSelect.getSelectBody());
            }
        }
    }

    private void analysisJoin(List<Join> joinList, PlainSelect plainSelect) {
        if (!CollectionUtils.isEmpty(joinList)) {
            for (Join join : joinList) {
                analysisFromItem(join.getRightItem(), plainSelect);
            }
        }
    }


    private void analysisSubJoin(SubJoin subJoin, PlainSelect plainSelect) {
        analysisFromItem(subJoin.getLeft(), plainSelect);
        //analysisJoin(subJoin.getJoinList(), plainSelect);
        analysisJoin(Arrays.asList(subJoin.getJoin()), plainSelect);
    }

    private void analysisSubSelect(SubSelect subSelect) {
        analysisSelectBody(subSelect.getSelectBody());
    }

    private void analysisTables(List<Table> tableList, PlainSelect plainSelect) {
        if (!CollectionUtils.isEmpty(tableList)) {
            for (Table t : tableList) {
                analysisTable(t, plainSelect);
            }
        }
    }

    private void analysisTable(Table table, PlainSelect plainSelect) {
        if (null != table) {
            log.info("### tableName : {} , alias : {} , setAlias {}", table.getName(), table.getAlias(), LOCAL_DATA.get().getAlias());
            if (StringUtils.isNotBlank(LOCAL_DATA.get().getAlias())) {
                //别名定位
                String alias = Optional.ofNullable(table).map(Table::getAlias).map(Alias::getName).orElse("");
                if (alias.equals(LOCAL_DATA.get().getAlias())) {
                    buildWhere(plainSelect);
                }
            } else {
                if (StringUtils.isNotBlank(LOCAL_DATA.get().getTableName())) {
                    //表名定位
                    if (table.getName().toUpperCase().equals(LOCAL_DATA.get().getTableName().toUpperCase())) {
                        buildWhere(plainSelect);
                    }
                }
            }
        }
    }

    /**
     * 构建where 条件
     *
     * @param plainSelect
     */
    private void buildWhere(PlainSelect plainSelect) {
        if (LOCAL_DATA.get() == null || StringUtils.isBlank(LOCAL_DATA.get().getWhereSql())) {
            return;
        }
        if (LOCAL_DATA.get().getExistCount().get() >= LOCAL_DATA.get().getAllowCount()) {
            return;
        }
        try {
            String whereSql = plainSelect.getWhere() != null ?
                    plainSelect.getWhere().toString() + " AND " + LOCAL_DATA.get().getWhereSql() : LOCAL_DATA.get().getWhereSql();
            Expression where = CCJSqlParserUtil.parseCondExpression(whereSql);
            plainSelect.setWhere(where);
            LOCAL_DATA.get().getExistCount().addAndGet(1);
        } catch (JSQLParserException e) {
            log.error(" buildWhere : {}", e);
            LOCAL_DATA.get().getExistCount().addAndGet(1);
        }
    }
}
