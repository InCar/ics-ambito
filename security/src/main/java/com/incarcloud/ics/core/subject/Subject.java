package com.incarcloud.ics.core.subject;


import com.incarcloud.ics.core.exception.UnAuthorizeException;
import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.privilege.Privilege;
import com.incarcloud.ics.core.session.Session;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Collection;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/19
 * @Version 1.0
 */
public interface Subject {
    boolean isPermitted(Privilege privilege);
    boolean isPermittedAllObjectPrivileges(Collection<Privilege> privileges);
    boolean isPermittedAllStringPrivileges(Collection<String> privileges);
    void checkPermitted(Privilege privilege) throws UnAuthorizeException;
    void checkPermittedAllObjectPrivileges(Collection<Privilege> privileges) throws UnAuthorizeException;
    void checkPermittedAllStringPrivileges(Collection<String> privileges) throws UnAuthorizeException;
    boolean hasRole(String roleIdentifier);
    void checkRole(String roleIdentifier) throws UnAuthorizeException;
    void checkAllRoles(Collection<String> roleIdentifiers) throws UnAuthorizeException;
    boolean hasAllRoles(Collection<String> roleIdentifiers);
    boolean isAccessibleForData(Serializable dataId, Class<?> clzz);
    Collection<String> getFilterCodes(Class<?> clzz);
    void login(AuthenticateToken authenticateToken) throws AuthenticationException;
    boolean isAuthenticated();
    Session getSession();
    Session getSession(boolean create);
    void logout();
    Principal getPrincipal();
    ServletRequest getServletRequest();
    ServletResponse getServletResponse();

}
