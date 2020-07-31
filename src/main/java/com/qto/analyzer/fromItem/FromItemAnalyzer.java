package com.qto.analyzer.fromItem;

import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.statement.select.FromItem;

/**
 * Description： fromitem 解析器
 * Author: liyou
 * Date: Created in 2020/7/29 14:10
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public interface FromItemAnalyzer {

    public FromItem analyzer(FromItem fromItem, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException;

}
