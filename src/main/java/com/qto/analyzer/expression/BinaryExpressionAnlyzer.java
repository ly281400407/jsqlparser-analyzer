package com.qto.analyzer.expression;

import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Description： 二元表达式解析器
 * Author: liyou
 * Date: Created in 2020/7/27 14:17
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class BinaryExpressionAnlyzer implements ExpressionAnlyzer{

    /**
     * 决策表达式解析器
     */
    private DecisionExpressionAnlyzer decisionExpressionAnlyzer;


    public BinaryExpressionAnlyzer(){
        decisionExpressionAnlyzer = new DecisionExpressionAnlyzer();
    }

    /**
     * 解析拼接数据
     * @param expression
     * @param tableConditionData
     * @return
     */
    public Expression analyzer(Expression expression, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(expression) || !ObjectUtils.allNotNull(tableConditionData)){
            return expression;
        }

        if(expression instanceof BinaryExpression) {

            BinaryExpression binaryExpression = (BinaryExpression) expression;

            Expression leftExpression = binaryExpression.getLeftExpression();
            if (ObjectUtils.allNotNull(leftExpression)) {
                leftExpression = decisionExpressionAnlyzer.analyzer(leftExpression, tableConditionData);
                binaryExpression.setLeftExpression(leftExpression);
            }

            Expression rightExpression = binaryExpression.getRightExpression();
            if (ObjectUtils.allNotNull(rightExpression)) {
                rightExpression = decisionExpressionAnlyzer.analyzer(rightExpression, tableConditionData);
                binaryExpression.setRightExpression(rightExpression);
            }
        }

        return expression;

    }

}
