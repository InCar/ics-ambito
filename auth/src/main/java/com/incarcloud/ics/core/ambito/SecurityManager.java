package com.incarcloud.ics.core.ambito;

import com.incarcloud.ics.core.access.Accessor;
import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.authc.Authenticator;
import com.incarcloud.ics.core.authz.Authorizer;
import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.session.SessionManager;
import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.core.subject.SubjectContext;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/19
 * @Version 1.0
 */
public interface SecurityManager extends Authenticator,Authorizer,Accessor,SessionManager {
    Subject login(AuthenticateToken authenticateToken, Subject subject) throws AuthenticationException;
    void logout(Subject subject);
//    Subject createSubject(Account account, AuthenticateToken authenticateToken, Subject subject);
    Subject createSubject(SubjectContext subjectContext);
}
