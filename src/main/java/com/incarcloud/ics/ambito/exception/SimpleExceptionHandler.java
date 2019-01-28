package com.incarcloud.ics.ambito.exception;

import com.incarcloud.ics.ambito.common.ErrorDefine;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.utils.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/7
 */
@ControllerAdvice(basePackages = {"com.incarcloud.ics.ambito.controller"})
public class SimpleExceptionHandler {
    private Logger logger = Logger.getLogger(SimpleExceptionHandler.class.getName());

    @ExceptionHandler(value = {Exception.class})
    public @ResponseBody
    JsonMessage handleExceptions(final Exception ex) {
        logger.info(ExceptionUtils.getStackTraceAsString(ex));
        return ErrorDefine.toErrorMessage(ex);
    }
}
