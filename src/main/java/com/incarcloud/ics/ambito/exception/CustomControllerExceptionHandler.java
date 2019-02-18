package com.incarcloud.ics.ambito.exception;


import com.incarcloud.ics.ambito.utils.ExceptionUtils;
import com.incarcloud.ics.core.exception.SecurityException;
import com.incarcloud.ics.core.handler.HttpSecurityExceptionHandler;
import com.incarcloud.ics.exception.AmbitoException;
import com.incarcloud.ics.log.Logger;
import com.incarcloud.ics.log.LoggerFactory;
import com.incarcloud.ics.pojo.ErrorDefine;
import com.incarcloud.ics.pojo.JsonMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.incarcloud.ics.pojo.ErrorDefine.UNKNOWN_EXCEPTION;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/7
 */
@ControllerAdvice(basePackages = {"com.incarcloud.ics.ambito.controller"})
@ResponseBody
public class CustomControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(CustomControllerExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public JsonMessage handleUnknownExceptions(final Exception ex) {
        return handleUndefinedException(ex);
    }

    @ExceptionHandler(value = AmbitoException.class)
    public JsonMessage handleAmbitoExceptions(final AmbitoException ex) {
        for(ErrorDefine err : ErrorDefine.values()){
            if(err.getCode().equals(ex.getCode())){
                parseAndLogStacktrace(ex);
                return err.toErrorMessage();
            }
        }
        return handleUndefinedException(ex);
    }

    @ExceptionHandler(value = SecurityException.class)
    public void handleSecurityExceptions(final SecurityException ex,
                                                HttpServletResponse response) {
        try {
            parseAndLogStacktrace(ex);
            HttpSecurityExceptionHandler.getInstance().handle(response, ex);
        } catch (IOException e) {
            handleUndefinedException(e);
        }
    }


    private String parseAndLogStacktrace(Exception ex){
        String stackTraceAsString = ExceptionUtils.getStackTraceAsString(ex);
        logger.info(stackTraceAsString);
        return stackTraceAsString;
    }

    private JsonMessage handleUndefinedException(Exception ex){
        return UNKNOWN_EXCEPTION.toErrorMessage(parseAndLogStacktrace(ex));
    }
}
