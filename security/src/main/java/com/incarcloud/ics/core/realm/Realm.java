package com.incarcloud.ics.core.realm;

import com.incarcloud.ics.core.access.AccessInfo;
import com.incarcloud.ics.core.authc.AuthenticateInfo;
import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.authz.AuthorizeInfo;
import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.principal.Principal;


/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/19
 * @Version 1.0
 */
public interface Realm {
    String getRealmName();

    AuthenticateInfo getAuthenticateInfo(AuthenticateToken authenticateToken) throws AuthenticationException;

    AuthorizeInfo getAuthorizeInfo(Principal principal);

    AccessInfo getAccessInfo(Principal principal);

}
