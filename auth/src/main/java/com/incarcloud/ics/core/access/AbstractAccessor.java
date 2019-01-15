package com.incarcloud.ics.core.access;

import com.incarcloud.ics.core.principal.Principal;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/9
 */
public abstract class AbstractAccessor implements Accessor {
    @Override
    public boolean isAccessibleForData(Principal principal, Serializable dataId) {
        return false;
    }

    @Override
    public Collection<String> assessibleOrgCodes(Principal principal) {
        return null;
    }
}
