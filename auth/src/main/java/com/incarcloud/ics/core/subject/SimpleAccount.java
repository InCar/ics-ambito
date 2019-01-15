package com.incarcloud.ics.core.subject;


import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.org.Organization;
import com.incarcloud.ics.core.privilege.Privilege;
import com.incarcloud.ics.core.role.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
public class SimpleAccount implements Account,Serializable {

    private static final long serialVersionUID = -7901219969575377672L;

    private final Principal principal;
    private Object credential;
    private List<Organization> organizations = new ArrayList<>();
    private List<Role> roles = new ArrayList<>();
    private List<Privilege> privileges = new ArrayList<>();
    private byte[] credentialsSalt;

    public SimpleAccount(Principal principal) {
        this.principal = principal;
    }

    public SimpleAccount(Principal principal, String password) {
        this(principal, password, null);
    }

    public SimpleAccount(Principal principal, String password, byte[] credentialsSalt) {
        this.principal = principal;
        this.credential = password;
        this.credentialsSalt = credentialsSalt;
    }

    @Override
    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredential() {
        return credential;
    }

    @Override
    public void setCredential(Object credential) {
        this.credential = credential;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public List<Privilege> getPrivileges() {
        return privileges;
    }

    @Override
    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public byte[] getCredentialsSalt() {
        return credentialsSalt;
    }

    public void setCredentialsSalt(byte[] credentialsSalt) {
        this.credentialsSalt = credentialsSalt;
    }
}
