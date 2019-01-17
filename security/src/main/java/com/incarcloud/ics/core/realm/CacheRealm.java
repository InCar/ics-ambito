package com.incarcloud.ics.core.realm;

import com.incarcloud.ics.core.authc.LogoutAware;
import com.incarcloud.ics.core.cache.Cache;
import com.incarcloud.ics.core.cache.CacheManager;
import com.incarcloud.ics.core.principal.Principal;

import java.util.logging.Logger;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
public abstract class CacheRealm implements Realm, LogoutAware {

    protected Logger logger = Logger.getLogger(getClass().getName());
    private String realmName;
    private CacheManager cacheManager;
    private boolean isCacheEnabled;

    public CacheRealm() {
        this(null);
    }

    public CacheRealm(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.isCacheEnabled = true;
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
    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    protected Cache<String,Object> doGetCache(String cacheName){
        if(isCacheEnabled() && getCacheManager() != null){
            return cacheManager.getCache(cacheName);
        }else {
            return null;
        }
    }

    @Override
    public void onLogout(Principal principal){
        doClearCache(principal);
    }

    protected void doClearCache(Principal principal){
    }
}
