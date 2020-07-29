package com.qto.analyzer.expression;

import com.qto.analyzer.selectBody.DecisionSelectBodyAnalyzer;
import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Description： 子查询解析器
 * Author: liyou
 * Date: Created in 2020/7/24 17:51
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class SubSelectAnalyzer implements ExpressionAnlyzer{

    private DecisionSelectBodyAnalyzer decisionSelectBodyAnalyzer;

    public SubSelectAnalyzer(){
        decisionSelectBodyAnalyzer = new DecisionSelectBodyAnalyzer();
    }

    public Expression analyzer(Expression expression, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(expression) || !ObjectUtils.allNotNull(tableConditionData)){
            return expression;
        }

        if(expression instanceof SubSelect){

            SubSelect subSelect = (SubSelect) expression;
            SelectBody selectBody = subSelect.getSelectBody();
            if(!ObjectUtils.allNotNull(selectBody)){
                selectBody = decisionSelectBodyAnalyzer.analyzer(selectBody, tableConditionData);
                subSelect.setSelectBody(selectBody);
            }

        }

        return expression;
    }
}
