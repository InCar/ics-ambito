package com.incarcloud.ics.core.aspect.handler;

import com.incarcloud.ics.core.aspect.anno.Logic;
import com.incarcloud.ics.core.aspect.anno.RequiresRoles;
import com.incarcloud.ics.core.exception.SecurityException;
import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.core.utils.CollectionUtils;

import java.lang.annotation.Annotation;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/30
 */
public class RoleAnnotationHandler extends AnnotationHandler {

    public RoleAnnotationHandler() {
        super(RequiresRoles.class);
    }

    @Override
    public void doAssertAuthMatch(Annotation annotation) throws SecurityException {
        if(!supports(annotation)){
            return;
        }
        RequiresRoles requiresRoles = (RequiresRoles) annotation;
        String[] roles = requiresRoles.value();
        if(roles.length == 0){
            return;
        }
        Subject subject = getSubject();
        if(requiresRoles.logic().equals(Logic.AND)){
            subject.checkAllRoles(CollectionUtils.asList(requiresRoles.value()));
        }

        if(requiresRoles.logic().equals(Logic.OR)){
            for(String role : requiresRoles.value()){
                if(subject.hasRole(role)){
                    return;
                }
            }
            //所有的角色不匹配的情况，抛出未授权异常
            subject.checkRole(roles[0]);
        }

    }
}
