package com.qto.analyzer.fromItem;

import com.qto.analyzer.expression.DecisionExpressionAnlyzer;
import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Description： 横向派生表查询分析器
 * Author: liyou
 * Date: Created in 2020/7/31 15:58
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class LateralSubSelectAnalyzer implements FromItemAnalyzer {

    public LateralSubSelectAnalyzer(){
        decisionExpressionAnlyzer = new DecisionExpressionAnlyzer();
    }

    DecisionExpressionAnlyzer decisionExpressionAnlyzer;

    public FromItem analyzer(FromItem fromItem, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(fromItem) && ObjectUtils.allNotNull(tableConditionData)){
            return fromItem;
        }

        if(fromItem instanceof LateralSubSelect){
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            SubSelect subSelect = lateralSubSelect.getSubSelect();

            if(ObjectUtils.allNotNull(subSelect)){

                subSelect = (SubSelect) decisionExpressionAnlyzer.analyzer(subSelect, tableConditionData);
                lateralSubSelect.setSubSelect(subSelect);
                fromItem = lateralSubSelect;
            }

        }

        return fromItem;
    }
}
