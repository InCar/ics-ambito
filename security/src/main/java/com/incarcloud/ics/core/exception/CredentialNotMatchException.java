package com.incarcloud.ics.core.exception;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public class CredentialNotMatchException extends AuthenticationException {

    public CredentialNotMatchException() {
        super();
    }

    public CredentialNotMatchException(String message) {
        super(message);
    }

    public CredentialNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public CredentialNotMatchException(Throwable cause) {
        super(cause);
    }
}
