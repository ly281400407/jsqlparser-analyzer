package com.qto.analyzer.expression;

import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Description： 空逻辑判断表达式
 * Author: liyou
 * Date: Created in 2020/7/29 17:04
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class IsNullExpressionAnalyzer implements ExpressionAnlyzer {

    public IsNullExpressionAnalyzer(){
        decisionExpressionAnlyzer = new DecisionExpressionAnlyzer();
    }

    private DecisionExpressionAnlyzer decisionExpressionAnlyzer;

    public Expression analyzer(Expression expression, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(expression) || !ObjectUtils.allNotNull(tableConditionData)){
            return expression;
        }

        if(expression instanceof IsNullExpression){

            IsNullExpression isNullExpression = (IsNullExpression) expression;
            Expression leftExpression = isNullExpression.getLeftExpression();

            if(ObjectUtils.allNotNull(leftExpression)) {
                leftExpression = decisionExpressionAnlyzer.analyzer(leftExpression, tableConditionData);
                isNullExpression.setLeftExpression(leftExpression);
                expression = isNullExpression;
            }

        }

        return expression;
    }
}
