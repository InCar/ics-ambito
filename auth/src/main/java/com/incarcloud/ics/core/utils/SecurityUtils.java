package com.incarcloud.ics.core.utils;

import com.incarcloud.ics.core.exception.UnavailableSecurityManagerException;
import com.incarcloud.ics.core.subject.DefaultSubject;
import com.incarcloud.ics.core.subject.DefaultSubjectContext;
import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.core.ambito.SecurityManager;
import com.incarcloud.ics.core.subject.SubjectContext;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/8
 */
public abstract class SecurityUtils {
    private static SecurityManager securityManager;

    public static Subject getSubject() {
        Subject subject = ThreadContext.getSubject();
        if (subject == null) {
            subject = new DefaultSubject(getSecurityManager());
            ThreadContext.bind(subject);
        }
        return subject;
    }

    public static Subject getSubject(ServletRequest servletRequest, ServletResponse servletResponse) {
        Subject subject = ThreadContext.getSubject();
        if (subject == null) {
            SubjectContext subjectContext = new DefaultSubjectContext();
            subjectContext.setSecurityManager(getSecurityManager());
            subjectContext.setServletRequest(servletRequest);
            subjectContext.setServletResponse(servletResponse);
            subject = getSecurityManager().createSubject(subjectContext);
            ThreadContext.bind(subject);
        }
        return subject;
    }


    public static void setSecurityManager(SecurityManager securityManager) {
        SecurityUtils.securityManager = securityManager;
    }

    public static SecurityManager getSecurityManager() throws UnavailableSecurityManagerException {
        SecurityManager securityManager = ThreadContext.getSecurityManager();
        if (securityManager == null) {
            securityManager = SecurityUtils.securityManager;
        }
        if (securityManager == null) {
            String msg = "No SecurityManager accessible to the calling code, either bound to the " +
                    ThreadContext.class.getName() + " or as a vm static singleton.  This is an invalid application " +
                    "configuration.";
            throw new UnavailableSecurityManagerException(msg);
        }
        return securityManager;
    }
}
