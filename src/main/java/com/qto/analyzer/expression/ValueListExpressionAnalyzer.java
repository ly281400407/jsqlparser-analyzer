package com.qto.analyzer.expression;

import com.qto.analyzer.itemList.DecisionItemsListAnalyzer;
import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ValueListExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Description： 值列表表达式解析器
 * Author: liyou
 * Date: Created in 2020/7/29 17:21
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class ValueListExpressionAnalyzer implements ExpressionAnlyzer {

    public ValueListExpressionAnalyzer(){
        decisionItemsListAnalyzer = new DecisionItemsListAnalyzer();
    }

    DecisionItemsListAnalyzer decisionItemsListAnalyzer;

    public Expression analyzer(Expression expression, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(expression) || !ObjectUtils.allNotNull(tableConditionData)){
            return expression;
        }

        if(expression instanceof ValueListExpression) {

            ValueListExpression valueListExpression = (ValueListExpression) expression;
            ExpressionList expressionList = valueListExpression.getExpressionList();
            if(ObjectUtils.allNotNull(expressionList)){
                expressionList = (ExpressionList) decisionItemsListAnalyzer.analyzer(expressionList, tableConditionData);
                valueListExpression.setExpressionList(expressionList);
                expression = valueListExpression;
            }

        }
        return expression;
    }
}
