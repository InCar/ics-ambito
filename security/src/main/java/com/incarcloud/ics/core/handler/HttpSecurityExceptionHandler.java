package com.incarcloud.ics.core.handler;

import com.incarcloud.ics.core.exception.SecurityException;
import com.incarcloud.ics.core.utils.Asserts;
import com.incarcloud.ics.core.utils.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/28
 */
public class HttpSecurityExceptionHandler implements HttpExceptionHandler {

    private static class Holder{
        private static HttpSecurityExceptionHandler INSTANCE = new HttpSecurityExceptionHandler();
    }

    public static HttpExceptionHandler getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public ErrorMessage handle(HttpServletResponse httpServletResponse, SecurityException exception) throws IOException{
        Asserts.assertNotNull(exception, "exception");
        String contentType = "application/json;charset=utf-8";
        httpServletResponse.setContentType(contentType);
        ErrorMessage errorMessage = getErrorMessage(exception);
        PrintWriter writer = httpServletResponse.getWriter();
        if(errorMessage != null){
            writer.write(errorMessage.toJsonString());
        }else {
            errorMessage = ErrorMessage.unknownMessage();
            writer.write(ErrorMessage.unknownMessage().toJsonString());
        }
        writer.flush();
        return errorMessage;
    }


    protected ErrorMessage getErrorMessage(SecurityException exception){
        ErrorMessage err = SecurityExceptionMessage.getErrorMessage(exception.getClass());
        if(err != null) {
            err.setError(ExceptionUtils.getStackTraceAsString(exception));
        }
        return err;
    }

}
