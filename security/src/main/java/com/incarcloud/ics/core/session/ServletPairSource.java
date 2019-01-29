package com.incarcloud.ics.core.session;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/8
 */
public interface ServletPairSource {
    ServletRequest getServletRequest();
    ServletResponse getServletResponse();
}
