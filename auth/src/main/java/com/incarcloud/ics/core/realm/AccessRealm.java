package com.incarcloud.ics.core.realm;

import com.incarcloud.ics.core.access.AccessInfo;
import com.incarcloud.ics.core.principal.Principal;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public abstract class AccessRealm extends AuthorizeRealm {

    @Override
    public AccessInfo getAccessInfo(Principal principal) {
        AccessInfo accessInfo = doGetAccessInfo(principal);
        return accessInfo;
    }

    protected abstract AccessInfo doGetAccessInfo(Principal principal);

}
