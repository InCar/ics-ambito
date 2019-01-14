package com.incarcloud.ics.core.authc;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/9
 */
public class AllAllowCredentialMatcher implements CredentialMatcher {

    @Override
    public void assertMatch(Object principle, Object credential) {
        //do nothing to let assert pass
    }
}
