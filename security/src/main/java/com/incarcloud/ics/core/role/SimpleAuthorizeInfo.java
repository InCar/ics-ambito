package com.incarcloud.ics.core.role;

import com.incarcloud.ics.core.authz.AuthorizeInfo;
import com.incarcloud.ics.core.privilege.Privilege;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public class SimpleAuthorizeInfo implements AuthorizeInfo, Serializable {

    private static final long serialVersionUID = -958931075733666722L;
    private Collection<String> roles;
    private Collection<Privilege> privileges;

    public SimpleAuthorizeInfo() {
        this.roles = new HashSet<>();
        this.privileges = new HashSet<>();
    }

    public SimpleAuthorizeInfo(Collection<String> roles, Collection<Privilege> privileges) {
        this.roles = roles;
        this.privileges = privileges;
    }


    @Override
    public Collection<String> getRoles() {
        return roles;
    }

    @Override
    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }
}
