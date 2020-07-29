package com.qto;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.Node;
import net.sf.jsqlparser.parser.SimpleNode;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

import java.io.StringReader;

/**
 * Description： TODO
 * Author: liyou
 * Date: Created in 2020/7/24 10:20
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class JsqlParserDemo {

    public static void main(String args[]) throws JSQLParserException {


        System.out.println(JsqlParserDemo.class);

        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Statement st=parserManager.parse(new StringReader("select * from pf_user pu, pro_owner_member pom, (select * from pf_user pu1 left join pro_owner po on pu1.id = po.user_id) u_user  where pu.id = pom.pf_user_id and pu.id = 1 and pu.name like concat('%', (select name from pf_user where 0 = is_delete ), '%') and pu.id in (1, 2, 3) and pu.id is not null and pom.owner_id in (select id from pro_owner)"));
        PlainSelect body = (PlainSelect)((Select) st).getSelectBody();
        Expression wexp = body.getWhere();
        //Expression expression =  wexp.getRightExpression();
        SimpleNode simpleNode = body.getASTNode();
        AndExpression andExpression;
        InExpression inExpression;
        EqualsTo equalsTo;
        Between between;

/*        Node node = simpleNode.jjtGetChild(0);
        Node childNode =  node.jjtGetChild(3);
        System.out.println(null!=childNode?childNode.toString():null);*/
/*        SimpleNode simpleNode = wexp.getASTNode();
        Integer num = simpleNode.jjtGetNumChildren();
        Node node = simpleNode.jjtGetChild(0);
        System.out.println(node.toString());
        System.out.println(simpleNode.toString());
        System.out.println(wexp.toString());*/
    }


}
