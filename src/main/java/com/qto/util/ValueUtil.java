package com.qto.util;

import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class ValueUtil {


    /**
     * Description： 值转换工具类
     * 目前只支持 java.lang.Integer
     *           java.lang.Long
     *           java.lang.Short,
     *           java.lang.Float,
     *           java.lang.Double,
     *           java.lang.String,
     *           java.util.Date,
     *           java.util.Collection
     * 其中Collection集合中的元素类型 只支持以上除Collection类型
     * 如果没有满足以上条件 会抛出 NoSupportColumOperationClassException
     * @throws NoSupportColumOperationClassException
     * Author: liyou
     * Date: Created in 2020/7/28 14:40
     * Company: qtopay
     * Copyright: Copyright (c) 2020
     */
    public static Expression convertToExpression(Object value) throws NoSupportColumOperationClassException {

        if(null==value){
            return new NullValue();
        }else if(value instanceof Integer){
            return new LongValue(((Integer) value).longValue());
        }else if(value instanceof Long){
            return new LongValue(((Long) value).longValue());
        }else if(value instanceof Short){
            return new LongValue(((Short) value).longValue());
        }else if(value instanceof Float){
            return new DoubleValue(value.toString());
        }else if(value instanceof Double){
            return new DoubleValue(value.toString());
        }else if(value instanceof String){
            return new StringValue(value.toString());
        }else if(value instanceof Date){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(value);
            return new DateValue(dateStr);
        }else{
            throw new NoSupportColumOperationClassException("不支持数据类型!");
        }

    }


    /**
     * Description： 值转换工具类
     * 目前只支持 java.util.Collection
     * 其中Collection集合中的元素类型  只支持以下类型
     *           java.lang.Integer
     *           java.lang.Long
     *           java.lang.Short
     *           java.lang.Float
     *           java.lang.Double
     *           java.lang.String
     *           java.util.Date
     * 如果没有满足以上条件 会抛出 NoSupportColumOperationClassException
     * @throws NoSupportColumOperationClassException
     * Author: liyou
     * Date: Created in 2020/7/28 14:40
     * Company: qtopay
     * Copyright: Copyright (c) 2020
     */
    public static ExpressionList convertToExpressionList(Object value) throws NoSupportColumOperationClassException {

        ExpressionList expressionList = new ExpressionList();
        List<Expression> expressions = new ArrayList<Expression>();

        if(null==value){
            expressions.add(new NullValue());
            expressionList.setExpressions(expressions);
            return expressionList;
        }

        if(value instanceof Collection){

            Collection list = (Collection) value;
            for(Object ob : list){
                if(null==ob){
                    continue;
                }
                Expression expression = convertToExpression(ob);
                if (expression instanceof ExpressionList){
                    throw new NoSupportColumOperationClassException("不支持数据类型!");
                }
                expressions.add(expression);
            }
            expressionList.setExpressions(expressions);
            return expressionList;

        }else{
            throw new NoSupportColumOperationClassException("不支持数据类型!");
        }

    }

}
