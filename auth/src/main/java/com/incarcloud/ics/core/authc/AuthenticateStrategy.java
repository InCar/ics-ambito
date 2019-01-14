package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.realm.Realm;
import com.incarcloud.ics.core.subject.Account;

import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public interface AuthenticateStrategy {
    Account authenticate(AuthenticateToken authenticateToken, List<Realm> realms);
}
