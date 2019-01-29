package com.incarcloud.ics.core.exception;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
public class AccountNotExistsException extends AuthenticationException {
    public AccountNotExistsException() {
        super();
    }

    public AccountNotExistsException(String message) {
        super(message);
    }

    public AccountNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotExistsException(Throwable cause) {
        super(cause);
    }
}
