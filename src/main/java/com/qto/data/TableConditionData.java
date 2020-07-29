package com.qto.data;


import java.util.HashMap;
import java.util.Map;

/**
 * Description: 表条件数据
 * Author: liyou
 * Date: Created in 2020/7/24 14:56
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class TableConditionData {

    private Map<String, TableOperation> tableOperationMap = new HashMap<String, TableOperation>();

    /**
     * 判断是否存在表条件
     * @param table
     * @return
     */
    public boolean contains(String table){
        return tableOperationMap.containsKey(table);
    }

    /**
     * 添加表条件判断信息
     * @param tableOperation
     * @return
     */
    public Integer add(TableOperation tableOperation){
        if(null==tableOperation){
            return 0;
        }

        if(null==tableOperation.getName() || "".equals(tableOperation.getName())){
            return 0;
        }
        tableOperationMap.put(tableOperation.getName(), tableOperation);
        return 1;

    }

    /**
     * 通过表名获取表条件判断信息
     * @param tableName
     * @return
     */
    public TableOperation getByTableName(String tableName){
        if(null==tableName || "".equals(tableName)){
            return null;
        }
        return tableOperationMap.get(tableName);
    }

}
