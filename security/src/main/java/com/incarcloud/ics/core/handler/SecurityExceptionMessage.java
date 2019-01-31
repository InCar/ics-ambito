package com.incarcloud.ics.core.handler;

import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.exception.AuthorizationException;
import com.incarcloud.ics.core.exception.SecurityException;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/28
 */
public enum SecurityExceptionMessage {
    UN_AUTHENTICATED(AuthenticationException.class, ErrorMessageConstants.UN_AUTHENTICATED),
    UN_AUTHORIZED(AuthorizationException.class, ErrorMessageConstants.UN_AUTHORIZED);

    private ErrorMessage errorMessage;
    private Class<? extends SecurityException> exceptionClass;

    SecurityExceptionMessage(Class<? extends SecurityException> exceptionClass, ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
        this.exceptionClass = exceptionClass;
    }

    public Class<? extends SecurityException> getExceptionClass() {
        return exceptionClass;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public static ErrorMessage getErrorMessage(Class<? extends SecurityException> exceptionClass){
        ErrorMessage errorMessage = null;
        if(exceptionClass == null){
            return ErrorMessage.unknownMessage();
        }
        for(SecurityExceptionMessage sm : SecurityExceptionMessage.values()){
            if(sm.getExceptionClass().isAssignableFrom(exceptionClass)){
                errorMessage = sm.getErrorMessage();
            }
        }
        return errorMessage;
    }
}
