package com.incarcloud.ics.core.handler;

import com.incarcloud.ics.core.exception.SecurityException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/28
 */
public interface HttpExceptionHandler {
    ErrorMessage handle(HttpServletResponse servletResponse, SecurityException e) throws IOException;
}
