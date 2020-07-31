package com.qto.analyzer.fromItem;

import com.qto.analyzer.itemList.DecisionItemsListAnalyzer;
import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.ValuesList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

/**
 * Description： 子关联查询解析器
 * Author: liyou
 * Date: Created in 2020/7/31 16:19
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class ValuesListFromItemAnalyzer implements  FromItemAnalyzer{

    public ValuesListFromItemAnalyzer(){
        decisionItemsListAnalyzer = new DecisionItemsListAnalyzer();
    }

    DecisionItemsListAnalyzer decisionItemsListAnalyzer;

    public FromItem analyzer(FromItem fromItem, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(fromItem) && ObjectUtils.allNotNull(tableConditionData)){
            return fromItem;
        }

        if(fromItem instanceof ValuesList){

            ValuesList valuesList = (ValuesList) fromItem;
            MultiExpressionList multiExpressionList = valuesList.getMultiExpressionList();
            if(ObjectUtils.allNotNull(multiExpressionList)){
                multiExpressionList = (MultiExpressionList) decisionItemsListAnalyzer.analyzer(multiExpressionList, tableConditionData);
                valuesList.setMultiExpressionList(multiExpressionList);
            }

        }

        return fromItem;
    }
}
