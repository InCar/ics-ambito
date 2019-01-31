package com.incarcloud.ics.core.handler;

import com.incarcloud.ics.core.exception.SecurityException;

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
public abstract class AbstractSecurityFilterExceptionHandler implements SecurityFilterExceptionHandler {

    private Class<? extends SecurityException> exceptionClass;

    public AbstractSecurityFilterExceptionHandler(Class<? extends SecurityException> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    protected ErrorMessage getErrorMessage(){
        return SecurityExceptionMessage.getErrorMessage(exceptionClass);
    }

    public Class<? extends SecurityException> getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(Class<? extends SecurityException> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        PrintWriter writer = null;
        try {
            String contentType = "application/json;charset=utf-8";
            httpServletResponse.setContentType(contentType);
            ErrorMessage errorMessage = getErrorMessage();
            writer = httpServletResponse.getWriter();
            if(errorMessage != null){
                writer.write(errorMessage.toJsonString());
            }else {
                writer.write(ErrorMessage.unknownMessage().toJsonString());
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            if(writer != null){
//                writer.close();
//            }
        }
    }

}
