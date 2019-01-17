package com.incarcloud.ics.core.subject;


import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.privilege.Privilege;
import com.incarcloud.ics.core.session.Session;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Collection;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/19
 * @Version 1.0
 */
public interface Subject {
    boolean isPermitted(Privilege privilege);
    boolean isPermittedAll(Collection<Privilege> privileges);
    boolean hasRole(String roleIdentifier);
    boolean hasAllRoles(Collection<String> roleIdentifiers);
    boolean isAccessiableForData(java.lang.String dataId);
    Collection<java.lang.String> getAccessiableOrg();
    void login(AuthenticateToken authenticateToken) throws AuthenticationException;
    boolean isAuthenticated();
    Session getSession();
    Session getSession(boolean create);
    void logout();
    Principal getPrincipal();
    ServletRequest getServletRequest();
    ServletResponse getServletResponse();
}
