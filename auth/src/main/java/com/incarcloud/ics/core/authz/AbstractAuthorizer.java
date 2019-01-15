package com.incarcloud.ics.core.authz;

import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.privilege.Privilege;
import com.incarcloud.ics.core.role.Role;

import java.util.Collection;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/9
 */
public abstract class AbstractAuthorizer implements Authorizer {
    @Override
    public boolean isPermitted(Principal account, Privilege privilege) {
        return false;
    }

    @Override
    public boolean isPermittedAll(Principal account, Collection<Privilege> privileges) {
        return false;
    }

    @Override
    public boolean hasRole(Principal account, Role role) {
        return false;
    }

    @Override
    public boolean hasAllRoles(Principal account, Collection<Role> roleList) {
        return false;
    }
}
