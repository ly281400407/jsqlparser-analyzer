package com.qto.exception;

/**
 * Description： 不支持操作值类型异常
 * Author: liyou
 * Date: Created in 2020/7/28 16:07
 * Company: qtopay
 * Copyright: Copyright (c) 2020
 */
public class NoSupportColumOperationClassException extends Exception {

    static final long serialVersionUID = -212297190745766939L;

    public NoSupportColumOperationClassException() {
        super();
    }

    public NoSupportColumOperationClassException(String message) {
        super(message);
    }

    public NoSupportColumOperationClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSupportColumOperationClassException(Throwable cause) {
        super(cause);
    }

}
