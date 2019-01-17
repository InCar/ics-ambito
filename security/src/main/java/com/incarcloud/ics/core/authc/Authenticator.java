package com.incarcloud.ics.core.authc;

/**
 * @Description 权限认证类
 * @Author ThomasChan
 * @Date 2018/12/19 5:13 PM
 * @Version 1.0
 */
public interface Authenticator {
    AuthenticateInfo authenticate(AuthenticateToken authenticateToken);
}
