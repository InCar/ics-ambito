package com.incarcloud.ics.core.filter;

import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.core.utils.CollectionUtils;
import com.incarcloud.ics.log.Logger;
import com.incarcloud.ics.log.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;


public class PermissionsAuthorizationFilter extends AuthorizationFilter {

    private Logger logger = LoggerFactory.getLogger(PermissionsAuthorizationFilter.class);

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        Subject subject = getSubject(request, response);
        String[] perms = (String[]) mappedValue;

        boolean isPermitted = true;
        if (perms != null && perms.length > 0) {
            if (!subject.isPermittedAllStringPrivileges(CollectionUtils.asSet(perms))) {
                isPermitted = false;
            }else {
                logger.debug("Is permitted all");
            }
        }

        return isPermitted;
    }
}
