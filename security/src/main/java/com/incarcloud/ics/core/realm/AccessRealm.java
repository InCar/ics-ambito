package com.incarcloud.ics.core.realm;

import com.incarcloud.ics.core.access.AccessInfo;
import com.incarcloud.ics.core.access.RequireAccessControl;
import com.incarcloud.ics.core.cache.Cache;
import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.utils.Asserts;
import com.incarcloud.ics.core.utils.ClassResolverUtils;
import com.incarcloud.ics.core.utils.StringUtils;

import java.util.Set;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public abstract class AccessRealm extends AuthorizeRealm {

    private static final String ACCESS_INFO_CACHE_NAME = "accessInfoCache";
    protected OrgAccessType orgAccessType;
    protected static final String FILTER_FIELD_NAME = "orgCode";
    protected Set<Class<?>> needAccessControlClass;

    public AccessRealm() {
        this(OrgAccessType.MANAGE, null);
    }

    public AccessRealm(OrgAccessType orgAccessType, String packageName) {
        this.orgAccessType = orgAccessType;
        getNeedAccessControlClass(packageName);
    }

    public OrgAccessType getOrgAccessType() {
        return orgAccessType;
    }

    public void setOrgAccessType(OrgAccessType orgAccessType) {
        this.orgAccessType = orgAccessType;
    }

    protected void getNeedAccessControlClass(String packageName){
        if(StringUtils.isBlank(packageName)){
            this.needAccessControlClass = ClassResolverUtils.findAnnotated(RequireAccessControl.class, "com.incarcloud", "com.incar");
        }else {
            this.needAccessControlClass = ClassResolverUtils.findAnnotated(RequireAccessControl.class, packageName);
        }
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
                logger.debug("No access data of found for user[{"+principal.getUserIdentity()+"}] !");
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
            logger.debug("No data of [{"+identifier+"}] store in cache [{"+ACCESS_INFO_CACHE_NAME+"}]!");
        }else {
            if(o instanceof AccessInfo){
                return (AccessInfo) o;
            }else {
                logger.debug("Cache data tableName [{"+o.getClass()+"}] mismatch with authenticationInfo!");
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

    protected boolean isAccessControlSupported(Class<?> aClass){
        if(aClass.isAnnotationPresent(RequireAccessControl.class)){
            try {
                aClass.getDeclaredField(FILTER_FIELD_NAME);
                return true;
            } catch (NoSuchFieldException e) {

                logger.info("No filed with tableName " + FILTER_FIELD_NAME + " in class " + aClass.getName());

                return false;
            }
        }else {
            logger.debug("The entity class need access control must configured with annotation " + RequireAccessControl.class.getName());
            return false;
        }
    }
}
