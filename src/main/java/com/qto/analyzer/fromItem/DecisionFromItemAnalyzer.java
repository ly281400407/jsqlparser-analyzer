package com.qto.analyzer.fromItem;

import com.qto.data.TableConditionData;
import net.sf.jsqlparser.statement.select.FromItem;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Description： 决策fromitem解析器
 * Author: liyou
 * Date: Created in 2020/7/29 14:12
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class DecisionFromItemAnalyzer implements FromItemAnalyzer{

    public DecisionFromItemAnalyzer(){
        fromItemAnalyzerMap = new HashMap<String, FromItemAnalyzer>();
    }

    private Map<String, FromItemAnalyzer> fromItemAnalyzerMap;


    public FromItem analyzer(FromItem fromItem, TableConditionData tableConditionData) {

        if(!ObjectUtils.allNotNull(fromItem)){
            return fromItem;
        }

        FromItemAnalyzer fromItemAnalyzer = fromItemAnalyzerMap.get(fromItem.getClass().getName());
        fromItem = fromItemAnalyzer.analyzer(fromItem, tableConditionData);
        return fromItem;

    }
}
