package com.incarcloud.ics.core.realm;

import com.incarcloud.ics.core.access.AccessInfo;
import com.incarcloud.ics.core.access.AccessTable;
import com.incarcloud.ics.core.access.FilterColumn;
import com.incarcloud.ics.core.cache.Cache;
import com.incarcloud.ics.core.exception.AccessDeniedException;
import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.utils.Asserts;

import javax.persistence.Table;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public abstract class AccessRealm extends AuthorizeRealm {

    public static final String ACCESS_INFO_CACHE_NAME = "accessInfoCache";
    protected AccessStrategy accessStrategy = AccessStrategy.MANAGE;

    public AccessRealm() {
    }

    public AccessRealm(AccessStrategy accessStrategy) {
        this.accessStrategy = accessStrategy;
    }

    @Override
    public AccessInfo getAccessInfo(Principal principal) {
        Asserts.assertNotNull(principal, "principal");
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


    protected void doClearCache(Principal principal){
        super.doClearCache(principal);
        Cache<String, Object> accessInfoCache = doGetCache(ACCESS_INFO_CACHE_NAME);
        if (accessInfoCache != null) {
            accessInfoCache.remove(principal.getUserIdentity());
        }
    }

    protected abstract AccessInfo doGetAccessInfo(Principal principal);

    protected void assertAccessControlSupported(Class<?> aClass){
        if(!aClass.isAnnotationPresent(Table.class) && !aClass.isAnnotationPresent(AccessTable.class)){
            throw new AccessDeniedException("The entity class need access control must configured with annotation " + AccessTable.class.getName() +" or " + Table.class.getName() );
        }
        if(aClass.isAnnotationPresent(FilterColumn.class)){
            throw new AccessDeniedException("The entity class need access control must specify the filter column with annotation " + FilterColumn.class.getName());
        }
    }
}
