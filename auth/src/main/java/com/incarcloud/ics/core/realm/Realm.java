package com.incarcloud.ics.core.realm;

import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.subject.Account;


/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/19
 * @Version 1.0
 */
public interface Realm {
    String getName();

    Account getUserDetail(AuthenticateToken principal) throws AuthenticationException;
}
