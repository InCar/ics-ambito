package com.incarcloud.ics.ambito.exception;

import com.incarcloud.ics.ambito.common.ErrorDefine;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.utils.ExceptionUtils;
import com.incarcloud.ics.core.exception.*;
import com.incarcloud.ics.core.exception.SecurityException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

import static com.incarcloud.ics.ambito.common.ErrorDefine.*;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/7
 */
@ControllerAdvice(basePackages = {"com.incarcloud.ics.ambito.controller"})
@ResponseBody
public class CustomControllerExceptionHandler {

    private Logger logger = Logger.getLogger(CustomControllerExceptionHandler.class.getName());

    @ExceptionHandler(value = Exception.class)
    public JsonMessage handleUnknownExceptions(final Exception ex) {
        return handleUnknownException(ex);
    }

    @ExceptionHandler(value = AmbitoException.class)
    public JsonMessage handleAmbitoExceptions(final AmbitoException ex) {
        for(ErrorDefine err : ErrorDefine.values()){
            if(err.getCode().equals(ex.getCode())){
                return err.toErrorMessage();
            }
        }
        return handleUnknownException(ex);
    }

    @ExceptionHandler(value = SecurityException.class)
    public JsonMessage handleSecurityExceptions(final SecurityException ex) {
        if(ex instanceof AccountNotExistsException){
            return ACCOUNT_NOT_EXISTS.toErrorMessage();
        }
        if(ex instanceof CredentialNotMatchException){
            return PASSWORD_NOT_MATCH.toErrorMessage();
        }
        if(ex instanceof UnAuthorizeException){
            return UN_AUTHORIZATION.toErrorMessage();
        }
        if(ex instanceof InvalidSessionException){
            return INVALID_SESSION.toErrorMessage();
        }
        return AUTHENTICATE_FAILED.toErrorMessage(parseAndLogStacktrace(ex));
    }


    private String parseAndLogStacktrace(Exception ex){
        String stackTraceAsString = ExceptionUtils.getStackTraceAsString(ex);
        logger.info(stackTraceAsString);
        return stackTraceAsString;
    }

    private JsonMessage handleUnknownException(Exception ex){
        return UNKNOWN_EXCEPTION.toErrorMessage(parseAndLogStacktrace(ex));
    }
}
