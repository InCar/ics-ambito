package com.incarcloud.ics.core.exception;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/8
 */
public class UnavailableSecurityManagerException extends SecurityException {
    private static final long serialVersionUID = -3061829807055871186L;

    public UnavailableSecurityManagerException() {
    }

    public UnavailableSecurityManagerException(String message) {
        super(message);
    }

    public UnavailableSecurityManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnavailableSecurityManagerException(Throwable cause) {
        super(cause);
    }

    public UnavailableSecurityManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
