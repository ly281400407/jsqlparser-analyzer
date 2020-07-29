package com.qto.data;

import lombok.Data;

import java.util.List;

/**
 * Description： 表条件操作信息
 * Author: liyou
 * Date: Created in 2020/7/24 17:07
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
@Data
public class TableOperation {

    /**
     * 表名
     */
    String name;

    /**
     * 字段条件
     */
    List<ColumOperation> columOperations;


}
