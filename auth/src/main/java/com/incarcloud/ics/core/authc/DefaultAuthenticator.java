package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.realm.Realm;
import com.incarcloud.ics.core.subject.Account;
import com.incarcloud.ics.core.utils.Asserts;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public class DefaultAuthenticator implements Authenticator {

    private List<Realm> realms;
    private AuthenticateStrategy authenticateStrategy;

    public DefaultAuthenticator(Realm realm) {
        Asserts.assertNotNull(realm, "Realm");
        this.realms = new ArrayList<>();
        this.realms.add(realm);
    }

    public DefaultAuthenticator(List<Realm> realms) {
        this(realms,  new AtLeastOneSuccessStrategy());
    }

    public DefaultAuthenticator(List<Realm> realms, AuthenticateStrategy authenticateStrategy) {
        Asserts.assertNotEmpty(realms, "Realm collection can't be empty");
        this.realms = realms;
        this.authenticateStrategy = authenticateStrategy;
    }

    @Override
    public Account authenticate(AuthenticateToken authenticateToken) {
        Account account = null;
        if(realms.size() == 1){
            account = doSingleRealmAuthenticate(authenticateToken);
        }else {
            account = doMultiRealmAuthenticate(authenticateToken);
        }
        return account;
    }

    private Account doMultiRealmAuthenticate(AuthenticateToken authenticateToken) {
        ensureAuthenticateStrategyConfigured();
        return authenticateStrategy.authenticate(authenticateToken, realms);
    }

    private Account doSingleRealmAuthenticate(AuthenticateToken authenticateToken) {
        return  this.realms.get(0).getUserDetail(authenticateToken);
    }

    private void ensureAuthenticateStrategyConfigured(){
        if(getAuthenticateStrategy() == null){
            setAuthenticateStrategy(new AtLeastOneSuccessStrategy());
        }
    }


    public AuthenticateStrategy getAuthenticateStrategy() {
        return authenticateStrategy;
    }

    public void setAuthenticateStrategy(AuthenticateStrategy authenticateStrategy) {
        this.authenticateStrategy = authenticateStrategy;
    }

    public List<Realm> getRealms() {
        return realms;
    }

    public void setRealms(List<Realm> realms) {
        this.realms = realms;
    }
}
