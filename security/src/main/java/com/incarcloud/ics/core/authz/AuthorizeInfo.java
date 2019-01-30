package com.incarcloud.ics.core.authz;

import com.incarcloud.ics.core.privilege.Privilege;

import java.util.Collection;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public interface AuthorizeInfo {
    Collection<String> getRoles();
    Collection<Privilege> getPrivileges();
}
