package com.qto.analyzer.expression;


import com.qto.data.TableConditionData;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import net.sf.jsqlparser.expression.Expression;

/**
 * Description： 抽象表达式解析器
 * Author: liyou
 * Date: Created in 2020/7/29 11:21
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public interface ExpressionAnlyzer {

    Expression analyzer(Expression expression, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException;

}
