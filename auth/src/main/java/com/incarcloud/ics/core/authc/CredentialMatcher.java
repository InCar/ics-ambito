package com.incarcloud.ics.core.authc;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public interface CredentialMatcher {
    void assertMatch(Object principle, Object credential);
}
