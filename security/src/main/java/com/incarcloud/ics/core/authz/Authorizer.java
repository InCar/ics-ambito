package com.incarcloud.ics.core.authz;

import com.incarcloud.ics.core.exception.UnAuthorizeException;
import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.privilege.Privilege;

import java.util.Collection;

/**
 * @Description 功能权限验证
 * @Author ThomasChan
 * @Date 2018/12/19 5:29 PM
 * @Version 1.0
 */
public interface Authorizer {
     boolean isPermitted(Principal account, Privilege privilege);
     boolean isPermittedAllObjectPrivileges(Principal account, Collection<Privilege> privileges);
     boolean isPermittedAllStringPrivileges(Principal account, Collection<String> privileges);
     void checkPermitted(Principal account, Privilege privilege) throws UnAuthorizeException;
     void checkPermittedAllObjectPrivileges(Principal account, Collection<Privilege> privileges) throws UnAuthorizeException;
     void checkPermittedAllStringPrivileges(Principal account, Collection<String> privileges) throws UnAuthorizeException;
     boolean hasRole(Principal account, String role);
     boolean hasAllRoles(Principal account, Collection<String> roleList);
     void checkRole(Principal account, String role) throws UnAuthorizeException;
     void checkAllRoles(Principal account, Collection<String> roleList) throws UnAuthorizeException;
}
