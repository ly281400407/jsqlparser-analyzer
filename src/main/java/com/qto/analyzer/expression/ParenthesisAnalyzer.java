package com.qto.analyzer.expression;

import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Description： 插入语解析器
 * Author: liyou
 * Date: Created in 2020/7/28 9:54
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class ParenthesisAnalyzer implements ExpressionAnlyzer{

    private DecisionExpressionAnlyzer decisionExpressionAnlyzer;

    public ParenthesisAnalyzer(){
        decisionExpressionAnlyzer = new DecisionExpressionAnlyzer();
    }

    public Expression analyzer(Expression expression, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(expression) || !ObjectUtils.allNotNull(tableConditionData)){
            return expression;
        }

        if(expression instanceof Parenthesis){
            Parenthesis parenthesis = (Parenthesis) expression;
            Expression subExpression = parenthesis.getExpression();

            if(ObjectUtils.allNotNull(subExpression)){
                subExpression = decisionExpressionAnlyzer.analyzer(subExpression, tableConditionData);
                parenthesis.setExpression(subExpression);
            }

        }

        return expression;
    }
}
