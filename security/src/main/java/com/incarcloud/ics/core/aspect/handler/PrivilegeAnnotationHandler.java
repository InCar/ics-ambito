package com.incarcloud.ics.core.aspect.handler;

import com.incarcloud.ics.core.aspect.anno.Logic;
import com.incarcloud.ics.core.aspect.anno.RequiresPrivileges;
import com.incarcloud.ics.core.exception.SecurityException;
import com.incarcloud.ics.core.privilege.Privilege;
import com.incarcloud.ics.core.privilege.WildcardPrivilege;
import com.incarcloud.ics.core.subject.Subject;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/30
 */
public class PrivilegeAnnotationHandler extends AnnotationHandler {

    public PrivilegeAnnotationHandler() {
        super(RequiresPrivileges.class);
    }

    @Override
    public void doAssertAuthMatch(Annotation annotation) throws SecurityException {
        RequiresPrivileges targetAnno = (RequiresPrivileges) annotation;
        String[] privileges = targetAnno.value();
        if(privileges.length == 0){
            return;
        }
        List<Privilege> objPrivileges = Stream.of(privileges).map(WildcardPrivilege::new).collect(Collectors.toList());
        Subject subject = getSubject();
        if(targetAnno.logic().equals(Logic.AND)){
            subject.checkPermittedAllObjectPrivileges(objPrivileges);
        }else if(targetAnno.logic().equals(Logic.OR)){
            for(Privilege privilege : objPrivileges){
                if(subject.isPermitted(privilege)){
                    return;
                }
            }
            //所有的权限不匹配的情况，抛出未授权异常
            subject.checkPermitted((objPrivileges).get(0));
        }
    }
}
