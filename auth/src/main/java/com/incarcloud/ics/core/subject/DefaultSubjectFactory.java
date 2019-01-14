package com.incarcloud.ics.core.subject;

import com.incarcloud.ics.core.Principle.Principal;
import com.incarcloud.ics.core.ambito.SecurityManager;
import com.incarcloud.ics.core.session.Session;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/12
 */
public class DefaultSubjectFactory implements SubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {

            SubjectContext wsc = new DefaultSubjectContext(context);
            SecurityManager securityManager = context.resolveSecurityManager();
            Session session = wsc.resolveSession();
//            boolean sessionEnabled = wsc.isSessionCreationEnabled();
            Principal principals = wsc.resolvePrincipals();
            boolean authenticated = wsc.resolveAuthenticated();
            String host = wsc.resolveHost();
            ServletRequest request = wsc.resoleveServletRequest();
            ServletResponse response = wsc.resoleveServletResponse();
            return new DefaultSubject(securityManager, session, host, authenticated, principals, request, response);

    }
}
