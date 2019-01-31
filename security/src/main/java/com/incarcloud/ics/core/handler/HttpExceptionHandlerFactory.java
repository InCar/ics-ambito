package com.incarcloud.ics.core.handler;

import com.incarcloud.ics.core.exception.SecurityException;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/31
 */
public interface HttpExceptionHandlerFactory {
    HttpExceptionHandler newHandler(SecurityException ex);
}
