package com.incarcloud.ics.core.exception;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/31
 */
public class UnAuthorizeException extends AuthorizationException {

    public UnAuthorizeException() {
    }

    public UnAuthorizeException(String message) {
        super(message);
    }

    public UnAuthorizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorizeException(Throwable cause) {
        super(cause);
    }
}
