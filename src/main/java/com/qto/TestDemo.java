package com.qto;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description： TODO
 * Author: liyou
 * Date: Created in 2020/7/27 14:45
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class TestDemo {

    public static void main(String args[]) throws JSQLParserException {

/*        Expression expression = new AndExpression(null, null);
        System.out.println(AndExpression.class.equals(expression.getClass()));*/
/*        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Statement st = parserManager.parse(new StringReader("select * from pf_user where user_id = 1 and user_type = 2 or (userId =1 and user_type = 1) and (select 2 from dual) in (1,2,3) and is_delete = true"));
        PlainSelect body = (PlainSelect)((Select) st).getSelectBody();
        Expression wexp = body.getWhere();*/

        AndExpression andExpression = new AndExpression(null, null);
        System.out.println(andExpression.getClass().toString());

    }

}
