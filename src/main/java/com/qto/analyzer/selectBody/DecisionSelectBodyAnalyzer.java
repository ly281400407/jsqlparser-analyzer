package com.qto.analyzer.selectBody;

import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.statement.select.SelectBody;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Description： 决策selectbody解析器
 * Author: liyou
 * Date: Created in 2020/7/29 15:13
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class DecisionSelectBodyAnalyzer implements SelectBodyAnalyzer {

    public DecisionSelectBodyAnalyzer(){
        selectBodyAnalyzerMap = new HashMap<String, SelectBodyAnalyzer>();
    }

    Map<String, SelectBodyAnalyzer> selectBodyAnalyzerMap;

    public SelectBody analyzer(SelectBody selectBody, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(selectBody) || !ObjectUtils.allNotNull(tableConditionData)){
            return selectBody;
        }

        SelectBodyAnalyzer selectBodyAnalyzer = selectBodyAnalyzerMap.get(selectBody.getClass().getName());
        if(ObjectUtils.allNotNull(selectBodyAnalyzer)){
            selectBody = selectBodyAnalyzer.analyzer(selectBody, tableConditionData);
        }

        return selectBody;
    }
}
