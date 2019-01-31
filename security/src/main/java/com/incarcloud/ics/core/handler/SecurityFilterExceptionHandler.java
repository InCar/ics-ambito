package com.incarcloud.ics.core.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/28
 */
public interface SecurityFilterExceptionHandler {
    void handle(HttpServletRequest httpServletRequest, HttpServletResponse servletResponse);
}
