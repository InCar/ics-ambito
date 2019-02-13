package com.incarcloud.ics.core.handler;

import com.incarcloud.ics.core.exception.SecurityException;
import com.incarcloud.ics.core.utils.Asserts;

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

    /**
     * 延迟加载，线程安全的单例
     */
    private static final class Holder{
        private static final HttpSecurityExceptionHandler INSTANCE = new HttpSecurityExceptionHandler();
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
        if(errorMessage == null){
            errorMessage = ErrorMessage.unknownMessage();
        }
        writer.write(errorMessage.toJsonString());
        writer.flush();
        return errorMessage;
    }


    protected ErrorMessage getErrorMessage(SecurityException exception){
        return SecurityExceptionMessage.getErrorMessage(exception.getClass());
    }

}
