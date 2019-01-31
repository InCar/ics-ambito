package com.incarcloud.ics.core.aspect.handler;

import com.incarcloud.ics.core.aspect.anno.RequiresAuthenticated;
import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.exception.SecurityException;
import com.incarcloud.ics.core.subject.Subject;

import java.lang.annotation.Annotation;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/30
 */
public class AuthenticatedAnnotationHandler extends AnnotationHandler {

    public AuthenticatedAnnotationHandler() {
        super(RequiresAuthenticated.class);
    }

    @Override
    public void doAssertAuthMatch(Annotation annotation) throws SecurityException {

        Subject subject = getSubject();
        if(!subject.isAuthenticated()){
            throw new AuthenticationException("Current user "+subject.getPrincipal()+" is unauthenticated");
        }
    }
}
