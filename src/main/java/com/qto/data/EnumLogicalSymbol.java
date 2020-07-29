package com.qto.data;

/**
 * Description： 表达式逻辑符号
 * Author: liyou
 * Date: Created in 2020/7/27 9:13
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public enum EnumLogicalSymbol {

    AND("and", "and", "且逻辑符"),
    OR("or", "or", "或逻辑符");

    private String key;
    private String value;
    private String decription;

    EnumLogicalSymbol(String key, String value, String decription){
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
    public static EnumLogicalSymbol findByKey(String key){

        if(null==key && "".equals(key)){
            return null;
        }else if(EnumLogicalSymbol.AND.getKey().equals(key)){
            return EnumLogicalSymbol.AND;
        }else if(EnumLogicalSymbol.OR.getKey().equals(key)){
            return EnumLogicalSymbol.OR;
        }

        return null;

    }

    /**
     * 判断是否为逻辑符
     * @param key
     * @return
     */
    public static boolean isLogicSymbol(String key){

        EnumLogicalSymbol enumLogicalSymbol = findByKey(key);
        if(null == enumLogicalSymbol){
            return false;
        }else{
            return false;
        }

    }

}
