package com.incarcloud.ics.core.role;

import com.incarcloud.ics.core.privilege.Privilege;

import java.util.Collection;

public interface Role {
    String getCode();
    Collection<Privilege> getPrivileges();
    void setPrivileges(Collection<Privilege> privileges);
}
