
package com.incarcloud.ics.core.aspect.intercepter;


/**
 * Aspect that adds a before advice for each invocation of an annotated method.
 */
public class AmbitoAnnotationAuthorizingAspect {

    private static final String pointCupExpression =
            "execution(@com.incarcloud.ics.core.aspect.anno.RequiresAccessible * *(..)) || " +
                    "execution(@com.incarcloud.ics.core.aspect.anno.RequiresAuthenticated * *(..)) || " +
                    "execution(@com.incarcloud.ics.core.aspect.anno.RequiresPrivileges * *(..)) || " +
                    "execution(@com.incarcloud.ics.core.aspect.anno.RequiresRoles * *(..))";


}
