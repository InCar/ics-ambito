package com.incarcloud.ics.core.exception;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/7
 */
public class InvalidSessionException extends SessionException {
    private static final long serialVersionUID = 2127357946960396350L;

    public InvalidSessionException() {
    }

    public InvalidSessionException(String message) {
        super(message);
    }

    public InvalidSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSessionException(Throwable cause) {
        super(cause);
    }

}
