package com.incarcloud.ics.core.role;

import com.incarcloud.ics.core.authz.AuthorizeInfo;

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
    private Collection<String> stringPrivileges;

    public SimpleAuthorizeInfo() {
        this.roles = new HashSet<>();
        this.stringPrivileges = new HashSet<>();
    }

    public SimpleAuthorizeInfo(Collection<String> roles, Collection<String> stringPrivileges) {
        this.roles = roles;
        this.stringPrivileges = stringPrivileges;
    }


    @Override
    public Collection<String> getRoles() {
        return roles;
    }

    @Override
    public Collection<String> getStringPrivileges() {
        return stringPrivileges;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public void setStringPrivileges(Collection<String> stringPrivileges) {
        this.stringPrivileges = stringPrivileges;
    }
}
