package com.qto.analyzer;

import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * Author: liyou
 * Date: Created in 2020/7/24 15:17
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class IncludeClassPool {

    private final static Class[] CLASS_ARR = {
            OrExpression.class,
            AndExpression.class,
            Parenthesis.class,
            Select.class,
            PlainSelect.class,
            SubSelect.class,
            EqualsTo.class,
            NotEqualsTo.class,
            InExpression.class,
            LikeExpression.class,
            GreaterThan.class,
            GreaterThanEquals.class,
            MinorThan.class,
            MinorThanEquals.class};


    /**
     * 判断是否包含需要解析的类
     * @param o
     * @return
     */
    public static boolean contain(Object o){

        if(null==o){
            return false;
        }

        for(Class forEachCls : CLASS_ARR){
            if(o.equals(forEachCls)){
                return true;
            }
        }
        return false;
    }

}
