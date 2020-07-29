package com.qto.data;

import lombok.Data;

/**
 * Description： 字段操作
 * Author: liyou
 * Date: Created in 2020/7/24 17:10
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
@Data
public class ColumOperation {

    /**
     * 字段名字
     */
    public String columName;

    /**
     * 预算操作符
     */
    public String operation;

    /**
     * 值
     */
    public Object value;

}
