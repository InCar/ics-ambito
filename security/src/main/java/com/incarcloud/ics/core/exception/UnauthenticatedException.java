
package com.incarcloud.ics.core.exception;

public class UnauthenticatedException extends AuthorizationException {
    public UnauthenticatedException() {
    }

    public UnauthenticatedException(String message) {
        super(message);
    }

    public UnauthenticatedException(Throwable cause) {
        super(cause);
    }

    public UnauthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
