package com.incarcloud.ics.core.realm;

import com.incarcloud.ics.core.authc.AuthenticateInfo;
import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.authc.CredentialMatcher;
import com.incarcloud.ics.core.authc.MD5PasswordMatcher;
import com.incarcloud.ics.core.cache.Cache;
import com.incarcloud.ics.core.exception.AccountNotExistsException;
import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.principal.Principal;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public abstract class AuthenticateRealm extends CacheRealm {

    public static final String AUTHENTICATE_CACHE_NAME = "authenticateCache";
    private CredentialMatcher credentialMatcher;

    public AuthenticateRealm() {
        this.credentialMatcher = new MD5PasswordMatcher();
    }

    public AuthenticateRealm(CredentialMatcher credentialMatcher) {
        this.credentialMatcher = credentialMatcher;
    }

    public CredentialMatcher getCredentialMatcher() {
        return credentialMatcher;
    }

    public void setCredentialMatcher(CredentialMatcher credentialMatcher) {
        this.credentialMatcher = credentialMatcher;
    }

    @Override
    public AuthenticateInfo getAuthenticateInfo(AuthenticateToken authenticateToken) throws AuthenticationException {
        //从缓存中认证信息
        AuthenticateInfo detail = getAuthenticateFromCache(authenticateToken.getPrincipal());
        if(detail == null){
            //缓存中没有认证信息，则调用子类的方法获取认证信息
            detail = doGetAuthenticateInfo(authenticateToken);
            if(detail != null){
                cacheAuthenticateInfo(authenticateToken.getPrincipal(), detail);
            }else {
                throw new AccountNotExistsException();
            }
        }
        //获取到认证信息后，进行密码校验
        doAssertMatch(detail, authenticateToken);
        return detail;
    }

    protected void doAssertMatch(AuthenticateInfo account, AuthenticateToken token){
        ensureCredentialMatcher();
        credentialMatcher.assertMatch(account, token.getCredential());
    }

    private void ensureCredentialMatcher(){
        if(getCredentialMatcher() == null){
            throw new AuthenticationException("CredentialMatcher must be configured in order to verify credential!");
        }
    }


    private void cacheAuthenticateInfo(String principal, AuthenticateInfo detail){
        if(isCacheEnabled() && getCacheManager() != null){
            Cache<Object, Object> cache = getCacheManager().getCache(AUTHENTICATE_CACHE_NAME);
            cache.put(principal, detail);
        }
    }

    private AuthenticateInfo getAuthenticateFromCache(String identifier){
        Cache<String, Object> cache = doGetCache(AUTHENTICATE_CACHE_NAME);
        if(cache == null){
            return null;
        }
        Object o = cache.get(identifier);
        if(o == null){
            logger.debug("No data of [{"+identifier+"}] store in cache [{"+AUTHENTICATE_CACHE_NAME+"}]!");
        }else {
            if(o instanceof AuthenticateInfo){
                return (AuthenticateInfo) o;
            }else {
                logger.debug("Cache data type [{"+o.getClass()+"}] mismatch with authenticationInfo!");
            }
        }
        return null;
    }

    protected abstract AuthenticateInfo doGetAuthenticateInfo(AuthenticateToken authenticateToken) throws AuthenticationException;

    @Override
    public void doClearCache(Principal principal){
        super.doClearCache(principal);
        Cache<String, Object> authenticateInfoCache = doGetCache(AUTHENTICATE_CACHE_NAME);
        if(authenticateInfoCache != null){
            authenticateInfoCache.remove(principal.getUserIdentity());
        }
    }

    public void clearAuthenticateCache(){
        Cache<String, Object> authenticateCache = doGetCache(AUTHENTICATE_CACHE_NAME);
        if(authenticateCache != null){
            authenticateCache.clear();
        }
    }
}
