
package com.incarcloud.ics.core.filter;


import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.core.utils.CollectionUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Set;

public class RolesAuthorizationFilter extends AuthorizationFilter {
    public RolesAuthorizationFilter() {
    }

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = this.getSubject(request, response);
        String[] rolesArray = ((String[])mappedValue);
        if (rolesArray != null && rolesArray.length != 0) {
            Set<String> roles = CollectionUtils.asSet(rolesArray);
            return subject.hasAllRoles(roles);
        } else {
            return true;
        }
    }

}
