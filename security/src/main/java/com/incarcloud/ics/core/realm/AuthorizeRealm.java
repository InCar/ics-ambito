package com.incarcloud.ics.core.realm;

import com.incarcloud.ics.core.authc.CredentialMatcher;
import com.incarcloud.ics.core.authz.AuthorizeInfo;
import com.incarcloud.ics.core.cache.Cache;
import com.incarcloud.ics.core.cache.CacheManager;
import com.incarcloud.ics.core.principal.Principal;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public abstract class AuthorizeRealm extends AuthenticateRealm{

    private static final String AUTHORIZE_CACHE_NAME = "authorizeCache";

    public AuthorizeRealm() {
        super();
    }

    public AuthorizeRealm(CredentialMatcher credentialMatcher) {
        super(credentialMatcher);
    }

    public AuthorizeRealm(CacheManager cacheManager, CredentialMatcher credentialMatcher) {
        super();
        this.setCacheManager(cacheManager);
        this.setCredentialMatcher(credentialMatcher);
    }

    public AuthorizeInfo getAuthorizeInfo(Principal principal){
        AuthorizeInfo authorizeInfo = getAuthorizeInfoFromCache(principal.getUserIdentity());
        if(authorizeInfo == null){
            authorizeInfo = doGetAuthorizeInfo(principal);
            if(authorizeInfo != null) {
                cacheAuthorizeInfo(authorizeInfo, principal);
            }
        }
        return authorizeInfo;
    }

    private void cacheAuthorizeInfo(AuthorizeInfo authorizeInfo, Principal principal) {
        if(isCacheEnabled() && getCacheManager() != null){
            Cache<String, Object> authorizeInfoCache = doGetCache(AUTHORIZE_CACHE_NAME);
            if(authorizeInfoCache != null) {
                authorizeInfoCache.put(principal.getUserIdentity(), authorizeInfo);
            }
        }
    }

    protected abstract AuthorizeInfo doGetAuthorizeInfo(Principal principal);

    private AuthorizeInfo getAuthorizeInfoFromCache(String identifier){
        Cache<String, Object> cache = doGetCache(AUTHORIZE_CACHE_NAME);
        if(cache == null){
            return null;
        }
        Object o = cache.get(identifier);
        if(o == null){
            logger.debug("No data of [{"+identifier+"}] store in cache [{"+AUTHORIZE_CACHE_NAME+"}]!");
        }else {
            if(o instanceof AuthorizeInfo){
                return (AuthorizeInfo) o;
            }else {
                logger.debug("Cache data type [{"+o.getClass()+"}] mismatch with authenticationInfo!");
            }
        }
        return null;
    }

    protected void doClearCache(Principal principal){
        super.doClearCache(principal);
        Cache<String, Object> authorizeInfoCache = doGetCache(AUTHORIZE_CACHE_NAME);
        if (authorizeInfoCache != null) {
            authorizeInfoCache.remove(principal.getUserIdentity());
        }
    }
}
