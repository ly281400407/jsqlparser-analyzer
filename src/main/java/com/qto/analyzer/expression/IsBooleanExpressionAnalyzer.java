package com.qto.analyzer.expression;

import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.IsBooleanExpression;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Description： 布尔逻辑表达式解析器
 * Author: liyou
 * Date: Created in 2020/7/29 17:13
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class IsBooleanExpressionAnalyzer implements ExpressionAnlyzer {

    public IsBooleanExpressionAnalyzer(){
        decisionExpressionAnlyzer = new DecisionExpressionAnlyzer();
    }

    private DecisionExpressionAnlyzer decisionExpressionAnlyzer;

    public Expression analyzer(Expression expression, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(expression) || !ObjectUtils.allNotNull(tableConditionData)){
            return expression;
        }

        if(expression instanceof  IsBooleanExpression){

            IsBooleanExpression isBooleanExpression = (IsBooleanExpression) expression;
            Expression leftExpression = isBooleanExpression.getLeftExpression();
            if(ObjectUtils.allNotNull(leftExpression)){
                leftExpression = decisionExpressionAnlyzer.analyzer(expression,tableConditionData);
                isBooleanExpression.setLeftExpression(leftExpression);
                expression = isBooleanExpression;
            }

        }

        return expression;
    }
}
