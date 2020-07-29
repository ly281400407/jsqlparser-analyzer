package com.qto.analyzer.expression;

import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.WhenClause;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Description： when case 表达式
 * Author: liyou
 * Date: Created in 2020/7/29 11:00
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class WhenClauseAnalyzer implements ExpressionAnlyzer{

    public WhenClauseAnalyzer(){
        decisionExpressionAnlyzer = new DecisionExpressionAnlyzer();
    }

    DecisionExpressionAnlyzer decisionExpressionAnlyzer;

    public Expression analyzer(Expression expression, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(expression) || !ObjectUtils.allNotNull(tableConditionData)){
            return expression;
        }

        if(expression instanceof WhenClause){

            WhenClause whenClause = (WhenClause) expression;

            Expression whenExpression = whenClause.getWhenExpression();
            if(ObjectUtils.allNotNull(whenClause)) {
                whenExpression = decisionExpressionAnlyzer.analyzer(whenExpression, tableConditionData);
                whenClause.setWhenExpression(whenExpression);
            }

            Expression thenExpression = whenClause.getThenExpression();
            if(ObjectUtils.allNotNull(thenExpression)) {
                thenExpression = decisionExpressionAnlyzer.analyzer(thenExpression, tableConditionData);
                whenClause.setThenExpression(thenExpression);
            }

        }

        return expression;
    }
}
