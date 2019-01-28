package com.incarcloud.ics.core.handler;

import com.incarcloud.ics.core.exception.SecurityException;
import com.incarcloud.ics.core.exception.UnauthenticatedException;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/28
 */
public enum SecurityMessage {
    UnAuthenticated(UnauthenticatedException.class, );

    private Class<? extends SecurityException> exceptionClzz;

    SecurityMessage(Class<? extends SecurityException> exceptionClzz) {
        this.exceptionClzz = exceptionClzz;
    }

    public Class<? extends SecurityException> getExceptionClzz() {
        return exceptionClzz;
    }

}
