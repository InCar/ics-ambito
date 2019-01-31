package com.incarcloud.ics.core.aspect.handler;


import com.incarcloud.ics.core.exception.SecurityException;
import com.incarcloud.ics.core.security.SecurityUtils;
import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.core.utils.Asserts;

import java.lang.annotation.Annotation;

public abstract class AnnotationHandler {
    protected Class<? extends Annotation> annotationClass;

    public AnnotationHandler(Class<? extends Annotation> anonClass) {
        this.annotationClass = anonClass;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        Asserts.assertNotNull(annotationClass, "annotationClass");
        this.annotationClass = annotationClass;
    }

    protected Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public void assertAuthMatch(Annotation annotation) throws SecurityException{
        if(supports(annotation)){
            doAssertAuthMatch(annotation);
        }
    }

    protected abstract void doAssertAuthMatch(Annotation annotation) throws SecurityException;

    protected boolean supports(Annotation annotation){
        return !(getAnnotationClass().equals(annotation.getClass()));
    }
}
