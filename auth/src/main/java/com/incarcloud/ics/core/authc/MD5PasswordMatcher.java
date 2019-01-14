package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.exception.CredentialNotMatchException;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public class MD5PasswordMatcher implements CredentialMatcher {

    @Override
    public void assertMatch(Object principle, Object credential) {

        if(!principle.equals(credential)){
            throw new CredentialNotMatchException();
        }
    }
}
