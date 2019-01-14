package com.incarcloud.ics.core.exception;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/19 5:45 PM
 * @Version 1.0
 */
public class AuthException extends RuntimeException {

    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static AuthException getIcsException(String message){
        return new AuthException(message);
    }

}
