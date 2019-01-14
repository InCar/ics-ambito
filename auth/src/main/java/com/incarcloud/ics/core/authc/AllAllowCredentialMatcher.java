package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.subject.Account;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/9
 */
public class AllAllowCredentialMatcher implements CredentialMatcher {

    @Override
    public void assertMatch(Account account, String credential) {
        //do nothing to let assert pass
    }
}
