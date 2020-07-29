package com.qto.analyzer.expression;

import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsBooleanExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Description： 表达式分析器
 * Author: liyou
 * Date: Created in 2020/7/29 11:03
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class DecisionExpressionAnlyzer implements ExpressionAnlyzer{

    public DecisionExpressionAnlyzer(){

        binaryExpressionAnlyzer = new BinaryExpressionAnlyzer();
        typeExpressionAnlyzerMap = new HashMap<String, ExpressionAnlyzer>();
        typeExpressionAnlyzerMap.put(AndExpression.class.getName(), new AndExpressionAnalyzer());
        typeExpressionAnlyzerMap.put(CaseExpression.class.getName(), new CaseExpressionAnalyzer());
        typeExpressionAnlyzerMap.put(InExpression.class.getName(), new InExpressionAnalyzer());
        typeExpressionAnlyzerMap.put(IsBooleanExpression.class.getName(), new IsBooleanExpressionAnalyzer());
        typeExpressionAnlyzerMap.put(IsNullExpression.class.getName(), new IsNullExpressionAnalyzer());
        typeExpressionAnlyzerMap.put(Parenthesis.class.getName(), new ParenthesisAnalyzer());
        typeExpressionAnlyzerMap.put(SubSelect.class.getName(), new SubSelectAnalyzer());
        typeExpressionAnlyzerMap.put(ValueListExpression.class.getName(), new ValueListExpressionAnalyzer());
        typeExpressionAnlyzerMap.put(WhenClause.class.getName(), new WhenClauseAnalyzer());

    }

    /**
     * 二元表达式解析器
     */
    private BinaryExpressionAnlyzer binaryExpressionAnlyzer;

    /**
     * 解析器池  通过表达式类型对解析器进行分类
     * 固定的解析器只能解析一种表达式
     */
    private Map<String, ExpressionAnlyzer> typeExpressionAnlyzerMap;

    public Expression analyzer(Expression expression, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(expression) || !ObjectUtils.allNotNull(tableConditionData)){
            return expression;
        }

        if(expression instanceof BinaryExpression){
            expression = binaryExpressionAnlyzer.analyzer(expression, tableConditionData);
        }else {

            String expressionKey = expression.getClass().toString();
            ExpressionAnlyzer expressionAnlyzer = typeExpressionAnlyzerMap.get(expressionKey);
            if(ObjectUtils.allNotNull(expressionAnlyzer)){
                expression = expressionAnlyzer.analyzer(expression, tableConditionData);
            }

        }

        return expression;

    }

}
