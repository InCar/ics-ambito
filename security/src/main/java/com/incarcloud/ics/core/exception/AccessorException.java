package com.incarcloud.ics.core.exception;


/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/19
 * @Version 1.0
 */
public class AccessorException extends SecurityException {
    public AccessorException() {
        super();
    }

    public AccessorException(String message) {
        super(message);
    }

    public AccessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessorException(Throwable cause) {
        super(cause);
    }
}
