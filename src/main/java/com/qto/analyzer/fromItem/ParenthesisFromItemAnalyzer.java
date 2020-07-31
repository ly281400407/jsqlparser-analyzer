package com.qto.analyzer.fromItem;

import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.ParenthesisFromItem;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Description： 插入语fromitem解析器
 * Author: liyou
 * Date: Created in 2020/7/31 16:08
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class ParenthesisFromItemAnalyzer implements FromItemAnalyzer {

    public ParenthesisFromItemAnalyzer(){
        decisionFromItemAnalyzer = new DecisionFromItemAnalyzer();
    }

    DecisionFromItemAnalyzer decisionFromItemAnalyzer;

    public FromItem analyzer(FromItem fromItem, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(fromItem) && ObjectUtils.allNotNull(tableConditionData)){
            return fromItem;
        }

        if(fromItem instanceof ParenthesisFromItem){
            ParenthesisFromItem parenthesisFromItem = (ParenthesisFromItem) fromItem;
            FromItem subFromItem = parenthesisFromItem.getFromItem();
            if(ObjectUtils.allNotNull(subFromItem)) {
                subFromItem = decisionFromItemAnalyzer.analyzer(subFromItem, tableConditionData);
                parenthesisFromItem.setFromItem(subFromItem);
            }
        }

        return fromItem;
    }
}
