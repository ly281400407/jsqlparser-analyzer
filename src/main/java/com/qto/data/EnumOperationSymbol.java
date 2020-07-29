package com.qto.data;

/**
 * Description： 运算符号
 * Author: liyou
 * Date: Created in 2020/7/27 9:13
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public enum EnumOperationSymbol {

    EQUAL("=", "equal", "等于"),
    NOT_EQUAL("!=", "not equal", "不等于"),
    IN("in", "in", "in"),
    NOT_IN("not in", "not in", "not in"),
    GREATER_THAN(">", "grater than", "大于"),
    GREATER_THAN_EQUAL(">=", "greater and equal than", "大于等于"),
    MINOR_THAN("<", "less than", "小于"),
    MINOR_THAN_EQUAL("<=", "less equal than", "小于等于"),
    LIKE("like", "like", "模糊匹配");

    private String key;
    private String value;
    private String decription;

    EnumOperationSymbol(String key, String value, String decription){
        this.key = key;
        this.value = value;
        this.decription = decription;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    /**
     * 通过key查找对应的枚举类
     * @param key
     * @return
     */
    public static EnumOperationSymbol findByKey(String key){
        if(null==key && "".equals(key)){
            return null;
        }else if (EnumOperationSymbol.EQUAL.getKey().equals(key)){
            return EnumOperationSymbol.EQUAL;
        }else if (EnumOperationSymbol.NOT_EQUAL.getKey().equals(key)){
            return EnumOperationSymbol.NOT_EQUAL;
        }else if (EnumOperationSymbol.IN.getKey().equals(key)){
            return EnumOperationSymbol.IN;
        }else if (EnumOperationSymbol.NOT_IN.getKey().equals(key)){
            return EnumOperationSymbol.NOT_IN;
        }else if (EnumOperationSymbol.GREATER_THAN.getKey().equals(key)){
            return EnumOperationSymbol.GREATER_THAN;
        }else if (EnumOperationSymbol.GREATER_THAN_EQUAL.getKey().equals(key)){
            return EnumOperationSymbol.GREATER_THAN_EQUAL;
        }else if (EnumOperationSymbol.MINOR_THAN.getKey().equals(key)){
            return EnumOperationSymbol.MINOR_THAN;
        }else if (EnumOperationSymbol.MINOR_THAN_EQUAL.getKey().equals(key)){
            return EnumOperationSymbol.MINOR_THAN_EQUAL;
        }else if (EnumOperationSymbol.LIKE.getKey().equals(key)){
            return EnumOperationSymbol.LIKE;
        }
        return null;

    }

    /**
     * 判断是否为运算符
     * @param key
     * @return
     */
    public static boolean isOperationSymbol(String key){

        EnumOperationSymbol operationSymbol = findByKey(key);
        if(null == operationSymbol){
            return false;
        }else{
            return true;
        }

    }

}
