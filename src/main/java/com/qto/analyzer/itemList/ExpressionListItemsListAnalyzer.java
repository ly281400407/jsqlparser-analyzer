package com.qto.analyzer.itemList;

import com.qto.analyzer.expression.DecisionExpressionAnlyzer;
import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

/**
 * Description： 表达式列表解析器
 * Author: liyou
 * Date: Created in 2020/7/31 16:47
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class ExpressionListItemsListAnalyzer implements ItemsListAnalyzer {

    public ExpressionListItemsListAnalyzer(){
        decisionExpressionAnlyzer = new DecisionExpressionAnlyzer();
    }

    DecisionExpressionAnlyzer decisionExpressionAnlyzer;

    public ItemsList analyzer(ItemsList itemsList, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(itemsList) || !ObjectUtils.allNotNull(tableConditionData)){
            return itemsList;
        }

        if(itemsList instanceof ExpressionList){

            ExpressionList expressionList = (ExpressionList) itemsList;
            List<Expression> expressions = expressionList.getExpressions();
            if(CollectionUtils.isNotEmpty(expressions)){

                for(Expression expression : expressions){
                    if(!ObjectUtils.allNotNull(expression)){
                        continue;
                    }
                    expression = decisionExpressionAnlyzer.analyzer(expression, tableConditionData);
                }

            }
            expressionList.setExpressions(expressions);

        }

        return null;
    }
}
