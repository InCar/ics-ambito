package com.incarcloud.ics.core.exception;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/19
 * @Version 1.0
 */
public class AuthenticationException extends AuthException {
    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
