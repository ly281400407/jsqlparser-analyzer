package com.qto.exception;

/**
 * Description： 字段操作类型异常
 * Author: liyou
 * Date: Created in 2020/7/28 11:49
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class ColumOperationException extends Exception {

    static final long serialVersionUID = -212297190745766939L;

    public ColumOperationException() {
        super();
    }

    public ColumOperationException(String message) {
        super(message);
    }

    public ColumOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ColumOperationException(Throwable cause) {
        super(cause);
    }

}
