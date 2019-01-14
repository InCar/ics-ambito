package com.incarcloud.ics.core.authz;

import com.incarcloud.ics.core.Principle.Principal;
import com.incarcloud.ics.core.privilege.Privilege;
import com.incarcloud.ics.core.role.Role;

import java.util.Collection;

/**
 * @Description 功能权限验证
 * @Author ThomasChan
 * @Date 2018/12/19 5:29 PM
 * @Version 1.0
 */
public interface Authorizer {
     boolean isPermitted(Principal account, Privilege privilege);
     boolean isPermittedAll(Principal account, Collection<Privilege> privileges);
     boolean hasRole(Principal account, Role role);
     boolean hasAllRoles(Principal account, Collection<Role> roleList);
}
