package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.realm.Realm;

import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public interface AuthenticateStrategy {
    AuthenticateInfo authenticate(AuthenticateToken authenticateToken, List<Realm> realms);
}
