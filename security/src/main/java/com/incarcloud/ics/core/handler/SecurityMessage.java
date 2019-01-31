package com.incarcloud.ics.core.handler;

import com.incarcloud.ics.core.exception.UnAuthorizeException;
import com.incarcloud.ics.core.exception.SecurityException;
import com.incarcloud.ics.core.exception.UnauthenticatedException;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/28
 */
public enum SecurityMessage {
    UN_AUTHENTICATED(UnauthenticatedException.class.getName(), ErrorMessageConstants.UN_AUTHENTICATED),
    UN_AUTHORIZED(UnAuthorizeException.class.getName(), ErrorMessageConstants.UN_AUTHORIZED);

    private ErrorMessage errorMessage;
    private String exceptionClassName;

    SecurityMessage(String exceptionClassName, ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
        this.exceptionClassName = exceptionClassName;
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public static ErrorMessage getErrorMessage(Class<? extends SecurityException> exceptionClass){
        if(exceptionClass == null){
            return null;
        }
        for(SecurityMessage sm : SecurityMessage.values()){
            if(sm.exceptionClassName.equals(exceptionClass.getName())){
                return sm.getErrorMessage();
            }
        }
        return null;
    }
}
