package com.qto.analyzer.itemList;

import com.qto.analyzer.expression.DecisionExpressionAnlyzer;
import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;

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


        return null;
    }
}
