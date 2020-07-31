package com.qto.analyzer.itemList;

import com.qto.analyzer.expression.DecisionExpressionAnlyzer;
import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

/**
 * Description： TODO
 * Author: liyou
 * Date: Created in 2020/7/31 18:12
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class MultiExpressionListItemListAnalyzer implements ItemsListAnalyzer {

    public MultiExpressionListItemListAnalyzer(){
        decisionItemsListAnalyzer = new DecisionItemsListAnalyzer();
    }

    DecisionItemsListAnalyzer decisionItemsListAnalyzer;


    public ItemsList analyzer(ItemsList itemsList, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(itemsList) || !ObjectUtils.allNotNull(tableConditionData)){
            return itemsList;
        }

        if(itemsList instanceof MultiExpressionList){
            MultiExpressionList multiExpressionList = (MultiExpressionList) itemsList;
            List<ExpressionList> expressionLists = multiExpressionList.getExprList();

            if(!CollectionUtils.isNotEmpty(expressionLists)){
                return itemsList;
            }

            multiExpressionList = new MultiExpressionList();
            for(ExpressionList expressionList : expressionLists){
                if(!ObjectUtils.allNotNull(expressionList)){
                    continue;
                }
                expressionList = (ExpressionList) decisionItemsListAnalyzer.analyzer(expressionList, tableConditionData);
                multiExpressionList.addExpressionList(expressionList);
            }
            itemsList = multiExpressionList;
        }

        return itemsList;
    }
}
