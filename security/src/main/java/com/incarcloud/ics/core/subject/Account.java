package com.incarcloud.ics.core.subject;

import com.incarcloud.ics.core.org.Organization;
import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.privilege.Privilege;

import java.io.Serializable;
import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/24
 */
public interface Account extends Serializable {
    Principal getPrincipal();
    Object getCredential();
    void setCredential(Object credential);
    List<String> getRoles();
    void setRoles(List<String> roles);
    List<Organization> getOrganizations();
    void setOrganizations(List<Organization> privileges);
    List<Privilege> getPrivileges();
    void setPrivileges(List<Privilege> privileges);
    byte[] getCredentialsSalt();
}
