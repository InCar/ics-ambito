//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.incarcloud.ics.core.subject;


import com.incarcloud.ics.core.Principle.Principal;
import com.incarcloud.ics.core.session.Session;

import java.util.logging.Logger;

public class DefaultSubjectDAO implements SubjectDAO {
    private static final Logger log = Logger.getLogger(DefaultSubjectDAO.class.getName());

    public DefaultSubjectDAO() {
    }

    protected boolean isSessionStorageEnabled(Subject subject) {
        return true;
    }


    public Subject save(Subject subject) {
        if (this.isSessionStorageEnabled(subject)) {
            this.saveToSession(subject);
        } else {
            log.fine("Session storage of subject state for Subject [{"+subject+"}] has been disabled: identity and authentication state are expected to be initialized on every request or invocation.");
        }

        return subject;
    }

    protected void saveToSession(Subject subject) {
        this.mergePrincipals(subject);
        this.mergeAuthenticationState(subject);
    }

    private static boolean isEmpty(Principal pc) {
        return pc == null ;
    }

    protected void mergePrincipals(Subject subject) {

//        if (subject.isRunAs() && subject instanceof DelegatingSubject) {
//            try {
//                Field field = DelegatingSubject.class.getDeclaredField("principals");
//                field.setAccessible(true);
//                currentPrincipals = (PrincipalCollection)field.get(subject);
//            } catch (Exception var5) {
//                throw new IllegalStateException("Unable to access DelegatingSubject principals property.", var5);
//            }
//        }
        Principal currentPrincipals = subject.getPrincipal();
        Session session = subject.getSession(false);
        if (session == null) {
            if (!isEmpty(currentPrincipals)) {
                session = subject.getSession();
                session.setAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY, currentPrincipals);
            }
        } else {
            Principal existingPrincipals = (Principal) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (isEmpty(currentPrincipals)) {
                if (!isEmpty(existingPrincipals)) {
                    session.removeAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                }
            } else {
                session.setAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY, currentPrincipals);
            }
        }

    }

    protected void mergeAuthenticationState(Subject subject) {
        Session session = subject.getSession(false);
        if (session == null) {
            if (subject.isAuthenticated()) {
                session = subject.getSession();
                session.setAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY, Boolean.TRUE);
            }
        } else {
            Boolean existingAuthc = (Boolean)session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY);
            if (subject.isAuthenticated()) {
                if (existingAuthc == null || !existingAuthc) {
                    session.setAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY, Boolean.TRUE);
                }
            } else if (existingAuthc != null) {
                session.removeAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY);
            }
        }

    }

    protected void removeFromSession(Subject subject) {
        Session session = subject.getSession(false);
        if (session != null) {
            session.removeAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY);
            session.removeAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        }

    }

    public void delete(Subject subject) {
        this.removeFromSession(subject);
    }
}
