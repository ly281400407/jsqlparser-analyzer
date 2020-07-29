package com.qto.analyzer.selectBody;

import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.statement.select.SelectBody;

/**
 * Description： selectBody 解析器
 * Author: liyou
 * Date: Created in 2020/7/29 15:11
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public interface SelectBodyAnalyzer {

    public SelectBody analyzer(SelectBody selectBody, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException;

}
