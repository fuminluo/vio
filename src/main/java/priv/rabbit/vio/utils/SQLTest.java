package priv.rabbit.vio.utils;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.AddAliasesVisitor;
import net.sf.jsqlparser.util.SelectUtils;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import net.sf.jsqlparser.util.deparser.SelectDeParser;

import java.util.HashMap;
import java.util.Map;

public class SQLTest {

    public static void main(String[] args) throws JSQLParserException {

      /*  String sql = "select * from user order by id";

        // 新建 MySQL Parser
        SQLStatementParser parser = new OracleStatementParser(sql);
        // 使用Parser解析生成AST，这里SQLStatement就是AST
        SQLStatement statement = parser.parseStatement();
        // 使用visitor来访问AST
        OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();
        statement.accept(visitor);


        System.out.println(visitor.getColumns());
        System.out.println(visitor.getOrderByColumns());*/

        String sql = "SELECT * FROM  (SELECT Rownum rn , t.rowid rid ,t.*,c.* FROM  t_valsetruleinputitem_aa t ,t_cloumn c WHERE a=b AND ROWNUM < 10 and t.id in (select id from t_cloumn where ROWNUM < 10))  tt WHERE tt.rn >  1";

        //String sql = "select rownum from ( select t1 from table1 t1 union all select t2.b,t3.c from table2 t2,table3 t3 where t2.b = t3.c) a where a.id = ?";

        //String sql = "SELECT * FROM dual";

        Statement stmt = CCJSqlParserUtil.parse(sql);
        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();

        PlainSelect plainSelect = (PlainSelect) selectBody;


        plainSelect(plainSelect);


        System.out.println(select.toString());
        // 此处根据where实际情况强转 最外层
/*
        EqualsTo equalsTo = (EqualsTo)where;
        System.out.println("Table:"+((Column)equalsTo.getLeftExpression()).getTable());
        System.out.println("Field:"+((Column)equalsTo.getLeftExpression()).getColumnName());
*/

    }

    public static boolean plainSelect(PlainSelect plainSelect) {
        if (!fromItem(plainSelect.getFromItem())) {
            if (extracted(plainSelect)) return true;
        }
        return true;
    }

    public static boolean fromItem(FromItem fromItem) {

        if (fromItem instanceof Table) {
            return false;
        }
        if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (null != subSelect.getSelectBody()) {
                PlainSelect plainSelect = (PlainSelect) subSelect.getSelectBody();
                if (!fromItem(plainSelect.getFromItem())) {

                    if (extracted(plainSelect)) return true;

                }
            }
        }
        return true;
    }

    private static boolean extracted(PlainSelect plainSelect) {
        if (null != plainSelect.getWhere()) {
            System.out.println(" getWhere : " + plainSelect.getWhere().toString());
        }
        Expression where = null;
        try {
            String whereSql = plainSelect.getWhere() != null ? plainSelect.getWhere().toString() + " AND userId = 2" : "  userId = 2";
            where = CCJSqlParserUtil.parseCondExpression(whereSql);
            plainSelect.setWhere(where);
            return true;
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return true;
    }
}
