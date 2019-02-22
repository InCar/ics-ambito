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
    public static final ErrorMessage CREDENTIAL_NOT_MATCH = ErrorMessage.of("密码错误", "401");
    public static final ErrorMessage INVALID_SESSION = ErrorMessage.of("会话失效", "401");
    public static final ErrorMessage ACCOUNT_NOT_EXISTS = ErrorMessage.of("账号不存在", "401");
    public static final ErrorMessage ACCOUNT_LOCKED = ErrorMessage.of("账号已禁用", "401");
}
