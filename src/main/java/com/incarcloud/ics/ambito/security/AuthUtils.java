package com.incarcloud.ics.ambito.security;

import com.incarcloud.ics.ambito.entity.UserBean;
import com.incarcloud.ics.core.principal.SimplePrincipal;
import com.incarcloud.ics.core.realm.*;
import com.incarcloud.ics.core.security.SecurityManager;
import com.incarcloud.ics.core.security.SecurityUtils;
import com.incarcloud.ics.core.session.Session;
import com.incarcloud.ics.core.subject.Subject;

import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/2/21
 */
public final class AuthUtils {

    private AuthUtils(){
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public static UserBean getCurrentUser(){
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            return (UserBean) session.getAttribute("myInfo");
        }catch (SecurityException e){
            return null;
        }
    }

    /**
     * 保存当前用户到会话中
     * @param userBean
     * @return
     */
    public static UserBean storeCurrentUser(UserBean userBean){
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            throw new SecurityException();
        }
        Session session = subject.getSession();
        session.setAttribute("myInfo", userBean);
        return userBean;
    }


    public static void clearAuthCache(){
        SecurityManager securityManager = SecurityUtils.getSecurityManager();
        List<Realm> realms = securityManager.getRealms();
        if(!realms.isEmpty()){
            Realm realm = realms.get(0);
            if(realm instanceof AccessRealm){
                ((AccessRealm) realm).clearCachedInfo();
            }
        }
    }

    public static void clearUserAuthCache(SimplePrincipal simplePrincipal){
        SecurityManager securityManager = SecurityUtils.getSecurityManager();
        List<Realm> realms = securityManager.getRealms();
        if(!realms.isEmpty()){
            Realm realm = realms.get(0);
            if(realm instanceof CacheRealm){
                ((CacheRealm) realm).doClearCache(simplePrincipal);
            }
        }
    }

}
