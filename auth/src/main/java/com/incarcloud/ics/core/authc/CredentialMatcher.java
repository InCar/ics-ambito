package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.subject.Account;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public interface CredentialMatcher {
    void assertMatch(Account authenticateInfo, String credential);
}
