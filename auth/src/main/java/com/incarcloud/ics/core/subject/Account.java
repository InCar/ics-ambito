package com.incarcloud.ics.core.subject;

import com.incarcloud.ics.core.Principle.Principal;
import com.incarcloud.ics.core.org.Organization;
import com.incarcloud.ics.core.privilege.Privilege;
import com.incarcloud.ics.core.role.Role;

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
    List<Role> getRoles();
    void setRoles(List<Role> roles);
    List<Organization> getOrganizations();
    void setOrganizations(List<Organization> privileges);
    List<Privilege> getPrivileges();
    void setPrivileges(List<Privilege> privileges);
}
