package com.incarcloud.ics.core.handler;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/28
 */
public class ErrorMessageConstants {
    public static final ErrorMessage UN_AUTHENTICATED = ErrorMessage.of("请先登录", "401");
    public static final ErrorMessage UN_AUTHORIZED = ErrorMessage.of("无访问权限", "403");
}
