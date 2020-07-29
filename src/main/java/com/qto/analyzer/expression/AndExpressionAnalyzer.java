package com.qto.analyzer.expression;

import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Description： 且表达式解析器
 * Author: liyou
 * Date: Created in 2020/7/27 11:39
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class AndExpressionAnalyzer implements ExpressionAnlyzer{

    public AndExpressionAnalyzer(){
        decisionExpressionAnlyzer = new DecisionExpressionAnlyzer();
    }

    DecisionExpressionAnlyzer decisionExpressionAnlyzer;


    /**
     * 解析表andExpression表达式
     * @param expression
     * @param tableConditionData
     * @return
     * @throws ColumOperationException
     * @throws NoSupportColumOperationClassException
     */
    public Expression analyzer(Expression expression, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(expression) || !ObjectUtils.allNotNull(tableConditionData)){
            return expression;
        }

        if(expression instanceof AndExpression) {

            AndExpression andExpression = (AndExpression) expression;

            Expression leftExpression = andExpression.getLeftExpression();
            if (ObjectUtils.allNotNull(leftExpression)) {
                leftExpression = decisionExpressionAnlyzer.analyzer(leftExpression, tableConditionData);
                andExpression.setLeftExpression(leftExpression);
            }

            Expression rightExpression = andExpression.getRightExpression();
            if (ObjectUtils.allNotNull(rightExpression)) {
                rightExpression = decisionExpressionAnlyzer.analyzer(rightExpression, tableConditionData);
                andExpression.setRightExpression(rightExpression);
            }
        }

        return expression;

    }

}
