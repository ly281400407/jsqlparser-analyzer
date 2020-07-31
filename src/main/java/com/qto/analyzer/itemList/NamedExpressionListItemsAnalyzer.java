package com.qto.analyzer.itemList;

import com.qto.analyzer.expression.DecisionExpressionAnlyzer;
import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.NamedExpressionList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

/**
 * Description： TODO
 * Author: liyou
 * Date: Created in 2020/7/31 18:26
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class NamedExpressionListItemsAnalyzer implements ItemsListAnalyzer {

    public NamedExpressionListItemsAnalyzer(){
        decisionExpressionAnlyzer = new DecisionExpressionAnlyzer();
    }

    DecisionExpressionAnlyzer decisionExpressionAnlyzer;

    public ItemsList analyzer(ItemsList itemsList, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(itemsList) || !ObjectUtils.allNotNull(tableConditionData)){
            return itemsList;
        }

        if(itemsList instanceof NamedExpressionList){

            NamedExpressionList namedExpressionList = (NamedExpressionList) itemsList;
            List<Expression> expressionList = namedExpressionList.getExpressions();

            if(!CollectionUtils.isNotEmpty(expressionList)){
                return itemsList;
            }

            for(Expression expression : expressionList){
                if(!ObjectUtils.allNotNull(expression)){
                    continue;
                }
                expression = decisionExpressionAnlyzer.analyzer(expression, tableConditionData);
            }
            namedExpressionList.setExpressions(expressionList);
            itemsList = namedExpressionList;

        }

        return itemsList;
    }
}
