package com.zoom.tools.exception;

/**
 * 自定义异常类
 */
public class ApiException extends RuntimeException {

    public ApiException() {
        super();
    }

    public ApiException(String s) {
        super(s);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    static final long serialVersionUID = -7670962073475845088L;
}

