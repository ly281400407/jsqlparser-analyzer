package com.qto.analyzer.expression;

import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

/**
 * Description： TODO
 * Author: liyou
 * Date: Created in 2020/7/29 16:42
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class CaseExpressionAnalyzer implements ExpressionAnlyzer {

    public CaseExpressionAnalyzer(){
        decisionExpressionAnlyzer = new DecisionExpressionAnlyzer();
    }

    DecisionExpressionAnlyzer decisionExpressionAnlyzer;

    public Expression analyzer(Expression expression, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(expression) || !ObjectUtils.allNotNull(tableConditionData)){
            return expression;
        }

        if(expression instanceof CaseExpression){

            CaseExpression caseExpression = (CaseExpression) expression;
            Expression switchExpression = caseExpression.getSwitchExpression();
            if(ObjectUtils.allNotNull(switchExpression)) {
                switchExpression = decisionExpressionAnlyzer.analyzer(switchExpression, tableConditionData);
                caseExpression.setSwitchExpression(switchExpression);
            }

            List<WhenClause> whenExpressions = caseExpression.getWhenClauses();
            if(CollectionUtils.isNotEmpty(whenExpressions)){

                for(WhenClause whenClause : whenExpressions){
                    if(!ObjectUtils.allNotNull(whenClause)){
                        continue;
                    }
                    whenClause = (WhenClause) decisionExpressionAnlyzer.analyzer(whenClause, tableConditionData);
                }
                caseExpression.setWhenClauses(whenExpressions);

            }

            Expression elseExpression = caseExpression.getElseExpression();
            if(ObjectUtils.allNotNull(elseExpression)){
                elseExpression = decisionExpressionAnlyzer.analyzer(expression, tableConditionData);
                caseExpression.setElseExpression(elseExpression);
            }

            expression = caseExpression;

        }

        return expression;
    }
}
