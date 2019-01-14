package com.incarcloud.ics.core.realm;

import com.incarcloud.ics.core.Principle.Principal;
import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.authc.CredentialMatcher;
import com.incarcloud.ics.core.authc.LogoutAware;
import com.incarcloud.ics.core.authc.MD5PasswordMatcher;
import com.incarcloud.ics.core.cache.Cache;
import com.incarcloud.ics.core.cache.CacheManager;
import com.incarcloud.ics.core.exception.AccountNotExistsException;
import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.subject.Account;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
public abstract class CacheRealm implements Realm, LogoutAware {

    protected Logger logger = Logger.getLogger(getClass().getName());
    private static final String ACCOUNT_INFO_CACHE_NAME = "accountInfo";
    private static final AtomicInteger INSTANCE_COUNT = new AtomicInteger();

    private String name;
    private CacheManager cacheManager;

    private boolean isCacheEnabled;
    private CredentialMatcher credentialMatcher;


    public CacheRealm() {
        this(new MD5PasswordMatcher());
    }

    public CacheRealm(CredentialMatcher credentialMatcher) {
        this.isCacheEnabled = true;
        this.name = getClass().getName()+INSTANCE_COUNT.getAndIncrement();
        this.credentialMatcher = credentialMatcher;
    }

    public boolean isCacheEnabled() {
        return isCacheEnabled;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        this.isCacheEnabled = cacheEnabled;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        afterCacheManagerSet();
    }

    protected void afterCacheManagerSet() {
    }


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Account getUserDetail(AuthenticateToken authenticateToken) throws AuthenticationException {
        Account detail = getUserDetailFromCache(authenticateToken.getPrincipal());
        if(detail == null){
            detail = doGetUserDetail(authenticateToken);
            if(detail != null){
                cacheAuthenticateInfo(authenticateToken, detail);
            }else {
                throw new AccountNotExistsException();
            }
        }
        doAssertMatch(detail.getCredential(), authenticateToken);
        return detail;
    }

    private void ensureCredentialMatcher(){
        if(getCredentialMatcher() == null){
            throw new AuthenticationException("CredentialMatcher must be configured in order to verify credential!");
        }
    }


    protected void doAssertMatch(Object account, AuthenticateToken principal){
        ensureCredentialMatcher();
        credentialMatcher.assertMatch(account, principal.getCredential());
    }


    private Account getUserDetailFromCache(String identifier){
        Cache<Object, Object> cache = null;
        if(getCacheManager() == null){
            return null;
        }
        if(!isCacheEnabled()){
            return null;
        }
        cache = cacheManager.getCache(ACCOUNT_INFO_CACHE_NAME);
        if(cache == null){
            logger.fine("Could not found Cache [{"+identifier+"}] from cacheManager!");
            return null;
        }

        Object o = cache.get(identifier);
        if(o == null){
            logger.fine("No data of [{"+identifier+"}] store in cache [{"+ACCOUNT_INFO_CACHE_NAME+"}]!");
        }else {
            if(cache instanceof Account){
                return (Account) cache;
            }else {
                logger.fine("Cache data type [{"+o.getClass()+"}] mismatch with authenticationInfo!");
            }
        }
        return null;
    }

    private void cacheAuthenticateInfo(AuthenticateToken authenticateToken, Account account){
        if(getCacheManager() != null && isCacheEnabled()) {
            Cache<Object, Object> cache = cacheManager.getCache(ACCOUNT_INFO_CACHE_NAME);
            cache.put(authenticateToken.getPrincipal(), account);
        }
    }

    @Override
    public void onLogout(Principal principal){
        doClearCache(principal);
    }

    protected void doClearCache(Principal principal){
        if(principal == null){
            return;
        }
        Cache<String, Account> cache = cacheManager.getCache(ACCOUNT_INFO_CACHE_NAME);
        if(cache != null){
            cache.remove(principal.getUserIdentity());
        }
    }

    public CredentialMatcher getCredentialMatcher() {
        return credentialMatcher;
    }

    public void setCredentialMatcher(CredentialMatcher credentialMatcher) {
        this.credentialMatcher = credentialMatcher;
    }

    protected abstract Account doGetUserDetail(AuthenticateToken principal) throws AuthenticationException;
}
