package com.qto.analyzer.itemList;

import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;

/**
 * Description： 列表解析器
 * Author: liyou
 * Date: Created in 2020/7/29 14:11
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public interface ItemsListAnalyzer {

    public ItemsList analyzer(ItemsList itemsList, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException;

}
