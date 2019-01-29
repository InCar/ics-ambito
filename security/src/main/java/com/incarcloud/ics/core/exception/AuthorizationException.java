package com.incarcloud.ics.core.exception;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/19
 * @Version 1.0
 */
public class AuthorizationException extends SecurityException {
    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException(Throwable cause) {
        super(cause);
    }
}
