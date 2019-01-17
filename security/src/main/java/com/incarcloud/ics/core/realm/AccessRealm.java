package com.incarcloud.ics.core.realm;

import com.incarcloud.ics.core.access.AccessInfo;
import com.incarcloud.ics.core.cache.Cache;
import com.incarcloud.ics.core.principal.Principal;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public abstract class AccessRealm extends AuthorizeRealm {

    private static final String ACCESS_INFO_CACHE_NAME = "accessInfoCacheName";

    @Override
    public AccessInfo getAccessInfo(Principal principal) {
        AccessInfo accessInfo = getAccessInfoFromCache(principal.getUserIdentity());
        if(accessInfo == null){
            accessInfo = doGetAccessInfo(principal);
            if(accessInfo != null) {
                cacheAccessInfo(accessInfo, principal);
            }else {
                logger.fine("No access data of found for user[{"+principal.getUserIdentity()+"}] !");
            }
        }
        return accessInfo;
    }

    protected void cacheAccessInfo(AccessInfo accessInfo, Principal principal){
        Cache<String, Object> accessInfoCache = doGetCache(ACCESS_INFO_CACHE_NAME);
        if(accessInfoCache != null) {
            accessInfoCache.put(principal.getUserIdentity(), accessInfo);
        }
    }

    private AccessInfo getAccessInfoFromCache(String identifier){
        Cache<String, Object> cache = doGetCache(ACCESS_INFO_CACHE_NAME);
        if(cache == null){
            return null;
        }
        Object o = cache.get(identifier);
        if(o == null){
            logger.fine("No data of [{"+identifier+"}] store in cache [{"+ACCESS_INFO_CACHE_NAME+"}]!");
        }else {
            if(o instanceof AccessInfo){
                return (AccessInfo) o;
            }else {
                logger.fine("Cache data type [{"+o.getClass()+"}] mismatch with authenticationInfo!");
            }
        }
        return null;
    }

    protected abstract AccessInfo doGetAccessInfo(Principal principal);

    protected void doClearCache(Principal principal){
        super.doClearCache(principal);
        Cache<String, Object> accessInfoCache = doGetCache(ACCESS_INFO_CACHE_NAME);
        if (accessInfoCache != null) {
            accessInfoCache.remove(principal.getUserIdentity());
        }
    }
}
