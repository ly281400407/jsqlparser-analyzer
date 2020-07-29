package com.qto.analyzer.expression;

import com.qto.analyzer.itemList.DecisionItemsListAnalyzer;
import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Description： 集合包含条件解析器具
 * Author: liyou
 * Date: Created in 2020/7/27 11:41
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class InExpressionAnalyzer implements ExpressionAnlyzer{

    private DecisionExpressionAnlyzer decisionExpressionAnlyzer;

    private DecisionItemsListAnalyzer decisionItemsListAnalyzer;


    public Expression analyzer(Expression expression, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(expression) || !ObjectUtils.allNotNull(tableConditionData)){
            return expression;
        }

        if(expression instanceof InExpression){

            InExpression inExpression = (InExpression) expression;
            Expression leftExpression = inExpression.getLeftExpression();
            if(ObjectUtils.allNotNull(leftExpression)) {
                leftExpression = decisionExpressionAnlyzer.analyzer(leftExpression, tableConditionData);
                inExpression.setLeftExpression(leftExpression);
            }

            ItemsList leftItemsList = inExpression.getLeftItemsList();
            if(ObjectUtils.allNotNull(leftItemsList)) {
                leftItemsList = decisionItemsListAnalyzer.analyzer(leftItemsList, tableConditionData);
                inExpression.setLeftItemsList(leftItemsList);
            }

            ItemsList rightItemsList = inExpression.getRightItemsList();
            if(ObjectUtils.allNotNull(rightItemsList)){
                rightItemsList = decisionItemsListAnalyzer.analyzer(rightItemsList, tableConditionData);
                inExpression.setRightItemsList(rightItemsList);
            }

        }

        return expression;
    }
}
