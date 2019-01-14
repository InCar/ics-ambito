package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.subject.Account;

/**
 * @Description 权限认证类
 * @Author ThomasChan
 * @Date 2018/12/19 5:13 PM
 * @Version 1.0
 */
public interface Authenticator {
    Account authenticate(AuthenticateToken authenticateToken);
}
