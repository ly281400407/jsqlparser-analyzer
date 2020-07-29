package com.qto.analyzer.selectBody;

import com.qto.analyzer.expression.DecisionExpressionAnlyzer;
import com.qto.data.*;
import com.qto.exception.ColumOperationException;
import com.qto.exception.NoSupportColumOperationClassException;
import com.qto.util.ValueUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.Stack;

/**
 * Description： 查询解析器
 * Author: liyou
 * Date: Created in 2020/7/24 15:38
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class PlainSelectAnalyzer implements SelectBodyAnalyzer{

    DecisionExpressionAnlyzer decisionExpressionAnlyzer;

    public SelectBody analyzer(SelectBody selectBody, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        if(!ObjectUtils.allNotNull(selectBody) || !ObjectUtils.allNotNull(tableConditionData)){
            return selectBody;
        }
        if(!(selectBody instanceof PlainSelect)){
            return selectBody;
        }

        PlainSelect plainSelect = (PlainSelect) selectBody;
        Expression where = plainSelect.getWhere();

        //主表解析
        FromItem mainTable = plainSelect.getFromItem();
        if(!ObjectUtils.allNotNull(mainTable) && (mainTable instanceof Table)){
            Table table = (Table) mainTable;
            if(tableConditionData.contains(table.getName())){
                Expression expression = createTableExpression(table, tableConditionData);
                if(ObjectUtils.allNotNull(expression)) {
                    if (ObjectUtils.allNotNull(where)) {
                        AndExpression andExpression = new AndExpression(where, expression);
                        where = andExpression;
                    } else {
                        where = expression;
                    }
                }
            }
        }

        List<Join> joins = plainSelect.getJoins();
        if(CollectionUtils.isEmpty(joins)){
            plainSelect.setWhere(where);
            return plainSelect;
        }

        //关联表解析
        for(Join join : joins){

            if(!ObjectUtils.allNotNull(join)){
                continue;
            }

            FromItem fromItem = join.getRightItem();
            if(!ObjectUtils.allNotNull(fromItem)){
                continue;
            }

            //子查询解析
            if(fromItem instanceof SubSelect){

                Expression expression = (Expression) fromItem;
                expression = decisionExpressionAnlyzer.analyzer(expression, tableConditionData);
                fromItem = (FromItem) expression;
                join.setRightItem(fromItem);

            }else if(fromItem instanceof Table){

                //管理表条件拼接
                Table joinFromTable = (Table) fromItem;
                Expression expression = createTableExpression(joinFromTable, tableConditionData);
                if(ObjectUtils.allNotNull(expression)) {
                    if (ObjectUtils.allNotNull(where)) {
                        AndExpression andExpression = new AndExpression(where, expression);
                        where = andExpression;
                    } else {
                        where = expression;
                    }
                }

            }

        }
        plainSelect.setJoins(joins);
        plainSelect.setWhere(where);

        return plainSelect;
    }

    /**
     * 创建对应表的表达式
     * @param table
     * @param tableConditionData
     * @return
     * @throws ColumOperationException
     * @throws NoSupportColumOperationClassException
     */
    private Expression createTableExpression(Table table, TableConditionData tableConditionData) throws ColumOperationException, NoSupportColumOperationClassException {

        TableOperation tableOperation = tableConditionData.getByTableName(table.getName());
        List<ColumOperation> columOperations = tableOperation.getColumOperations();
        if(CollectionUtils.isNotEmpty(columOperations)){
            Expression expression = createExpression(columOperations, table);
            return expression;
        }

        return null;
    }

    /**
     * 创建表达式
     * @param columOperations
     * @param table
     * @return
     * @throws ColumOperationException
     * @throws NoSupportColumOperationClassException
     */
    private Expression createExpression(List<ColumOperation> columOperations, Table table) throws ColumOperationException, NoSupportColumOperationClassException {



        Stack<Expression> expressionStack = new Stack<Expression>();
        for(ColumOperation columOperation : columOperations){

            if(null==columOperation){
                continue;
            }

            //逻辑符号操作
            if(EnumLogicalSymbol.isLogicSymbol(columOperation.getOperation())){

                if(expressionStack.size()<2){
                    throw new ColumOperationException("条件表达式不完整！");
                }

                Expression leftExpression = expressionStack.pop();
                Expression rightExpression = expressionStack.pop();
                if(EnumLogicalSymbol.AND.getKey().equals(columOperation.getOperation())){
                    AndExpression andExpression = new AndExpression(leftExpression, rightExpression);
                    expressionStack.push(andExpression);
                }else if(EnumLogicalSymbol.OR.getKey().equals(columOperation.getOperation())){
                    OrExpression orExpression = new OrExpression(leftExpression, rightExpression);
                    expressionStack.push(orExpression);
                }

            }else if(EnumOperationSymbol.isOperationSymbol(columOperation.getOperation())){

                //运算符号操作
                Expression expression = createOperationExpression(columOperation, table);
                expressionStack.push(expression);

            }

        }

        if(expressionStack.size()==0){
            return null;
        }else if(expressionStack.size()==1){
            return expressionStack.pop();
        }else{
            throw new ColumOperationException("条件表达式不完整！");
        }
    }

    /**
     * 创建操作表达式
     * @param columOperation
     * @param table
     * @return
     */
    private Expression createOperationExpression(ColumOperation columOperation, Table table) throws NoSupportColumOperationClassException {


        if(EnumOperationSymbol.EQUAL.getKey().equals(columOperation.getOperation())){
            return createEqualExpression(columOperation, table);
        }else if(EnumOperationSymbol.NOT_EQUAL.getKey().equals(columOperation.getOperation())){
            return createNotEqualExpression(columOperation, table);
        }else if(EnumOperationSymbol.IN.getKey().equals(columOperation.getOperation())){
            return createInExpression(columOperation, table);
        }else if(EnumOperationSymbol.NOT_IN.getKey().equals(columOperation.getOperation())){
            return createNotInExpression(columOperation, table);
        }else if(EnumOperationSymbol.GREATER_THAN.getKey().equals(columOperation.getOperation())){
            return createGreaterThanExpression(columOperation, table);
        }else if(EnumOperationSymbol.GREATER_THAN_EQUAL.getKey().equals(columOperation.getOperation())){
            return createGreaterThanEqualsExpression(columOperation, table);
        }else if(EnumOperationSymbol.MINOR_THAN.getKey().equals(columOperation.getOperation())){
            return createMinorThanExpression(columOperation, table);
        }else if(EnumOperationSymbol.MINOR_THAN_EQUAL.getKey().equals(columOperation.getOperation())){
            return createMinorThanEqualsExpression(columOperation, table);
        }else if(EnumOperationSymbol.LIKE.getKey().equals(columOperation.getOperation())){
            return createLikeExpression(columOperation, table);
        }

        return null;

    }

    /**
     * 创建equal表达式
     * @param columOperation
     * @param table
     * @return
     */
    private Expression createEqualExpression(ColumOperation columOperation, Table table) throws NoSupportColumOperationClassException {

        EqualsTo equalsTo = new EqualsTo();
        Column column = new Column();
        column.setColumnName(columOperation.getColumName());
        column.setTable(table);

        equalsTo.setLeftExpression(column);
        Expression valueExpression = ValueUtil.convertToExpression(columOperation.getValue());
        equalsTo.setRightExpression(valueExpression);
        return  equalsTo;
    }

    /**
     * 创建not equal表达式
     * @param columOperation
     * @param table
     * @return
     */
    private Expression createNotEqualExpression(ColumOperation columOperation, Table table) throws NoSupportColumOperationClassException {

        NotEqualsTo notEqualsTo = new NotEqualsTo();
        Column column = new Column();
        column.setColumnName(columOperation.getColumName());
        column.setTable(table);

        notEqualsTo.setLeftExpression(column);
        Expression valueExpression = ValueUtil.convertToExpression(columOperation.getValue());
        notEqualsTo.setRightExpression(valueExpression);
        return  notEqualsTo;

    }

    /**
     * 创建in表达式
     * @param columOperation
     * @param table
     * @return
     * @throws NoSupportColumOperationClassException
     */
    private Expression createInExpression(ColumOperation columOperation, Table table) throws NoSupportColumOperationClassException {

        InExpression inExpression = new InExpression();
        Column column = new Column();
        column.setColumnName(columOperation.getColumName());
        column.setTable(table);

        inExpression.setLeftExpression(column);
        ExpressionList expressionList = ValueUtil.convertToExpressionList(columOperation.getValue());
        inExpression.setRightItemsList(expressionList);
        return inExpression;

    }

    /**
     * 创建 not in 表达式
     * @param columOperation
     * @param table
     * @return
     * @throws NoSupportColumOperationClassException
     */
    private Expression createNotInExpression(ColumOperation columOperation, Table table) throws NoSupportColumOperationClassException {

        InExpression notInExpression = new InExpression();
        Column column = new Column();
        column.setColumnName(columOperation.getColumName());
        column.setTable(table);
        notInExpression.setLeftExpression(column);
        notInExpression.setNot(true);

        ExpressionList expressionList = ValueUtil.convertToExpressionList(columOperation.getValue());
        notInExpression.setRightItemsList(expressionList);
        return notInExpression;

    }

    /**
     * 创建greate than表达式
     * @param columOperation
     * @param table
     * @return
     * @throws NoSupportColumOperationClassException
     */
    private Expression createGreaterThanExpression(ColumOperation columOperation, Table table) throws NoSupportColumOperationClassException {

        GreaterThan greaterThan = new GreaterThan();
        Column column = new Column();
        column.setColumnName(columOperation.getColumName());
        column.setTable(table);
        greaterThan.setLeftExpression(column);

        Expression valueExpression = ValueUtil.convertToExpression(columOperation.getValue());
        greaterThan.setRightExpression(valueExpression);
        return greaterThan;
    }

    /**
     * 创建greate than表达式
     * @param columOperation
     * @param table
     * @return
     * @throws NoSupportColumOperationClassException
     */
    private Expression createGreaterThanEqualsExpression(ColumOperation columOperation, Table table) throws NoSupportColumOperationClassException {

        GreaterThanEquals greaterThanEquals = new GreaterThanEquals();
        Column column = new Column();
        column.setColumnName(columOperation.getColumName());
        column.setTable(table);
        greaterThanEquals.setLeftExpression(column);

        Expression valueExpression = ValueUtil.convertToExpression(columOperation.getValue());
        greaterThanEquals.setRightExpression(valueExpression);
        return greaterThanEquals;
    }

    /**
     * 创建 minorThan 表达式
     * @param columOperation
     * @param table
     * @return
     * @throws NoSupportColumOperationClassException
     */
    private Expression createMinorThanExpression(ColumOperation columOperation, Table table) throws NoSupportColumOperationClassException {

        MinorThan minorThan = new MinorThan();
        Column column = new Column();
        column.setColumnName(columOperation.getColumName());
        column.setTable(table);
        minorThan.setLeftExpression(column);

        Expression valueExpression = ValueUtil.convertToExpression(columOperation.getValue());
        minorThan.setRightExpression(valueExpression);
        return minorThan;

    }

    /**
     * 创建 minorThanEquals 表达式
     * @param columOperation
     * @param table
     * @return
     * @throws NoSupportColumOperationClassException
     */
    private Expression createMinorThanEqualsExpression(ColumOperation columOperation, Table table) throws NoSupportColumOperationClassException {

        MinorThanEquals minorThanEquals = new MinorThanEquals();
        Column column = new Column();
        column.setColumnName(columOperation.getColumName());
        column.setTable(table);
        minorThanEquals.setLeftExpression(column);

        Expression valueExpression = ValueUtil.convertToExpression(columOperation.getValue());
        minorThanEquals.setRightExpression(valueExpression);
        return minorThanEquals;

    }


    /**
     * 创建 minorThanEquals 表达式
     * @param columOperation
     * @param table
     * @return
     * @throws NoSupportColumOperationClassException
     */
    private Expression createLikeExpression(ColumOperation columOperation, Table table) throws NoSupportColumOperationClassException {

        LikeExpression minorThanEquals = new LikeExpression();
        Column column = new Column();
        column.setColumnName(columOperation.getColumName());
        column.setTable(table);
        minorThanEquals.setLeftExpression(column);

        Expression valueExpression = ValueUtil.convertToExpression(columOperation.getValue());
        minorThanEquals.setRightExpression(valueExpression);
        return minorThanEquals;

    }

}
