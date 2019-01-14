package com.incarcloud.ics.core.exception;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/13
 */
public class UnknownSessionException extends SessionException {
    private static final long serialVersionUID = 379256424904176272L;

    public UnknownSessionException() {
    }

    public UnknownSessionException(String message) {
        super(message);
    }

    public UnknownSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownSessionException(Throwable cause) {
        super(cause);
    }

    public UnknownSessionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
