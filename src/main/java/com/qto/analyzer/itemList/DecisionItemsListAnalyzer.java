package com.qto.analyzer.itemList;

import com.qto.analyzer.fromItem.DecisionFromItemAnalyzer;
import com.qto.data.TableConditionData;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;

import java.util.HashMap;
import java.util.Map;

/**
 * Description： 决策itemList解析器
 * Author: liyou
 * Date: Created in 2020/7/29 14:23
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class DecisionItemsListAnalyzer implements ItemsListAnalyzer {

    public DecisionItemsListAnalyzer(){
        itemsListAnalyzerMap = new HashMap<String, ItemsListAnalyzer>();
    }

    Map<String, ItemsListAnalyzer> itemsListAnalyzerMap;

    public ItemsList analyzer(ItemsList itemsList, TableConditionData tableConditionData) {
        return null;
    }
}
