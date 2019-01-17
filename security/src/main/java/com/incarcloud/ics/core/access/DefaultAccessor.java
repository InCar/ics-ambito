package com.incarcloud.ics.core.access;

import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.realm.Realm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/9
 */
public class DefaultAccessor implements Accessor{

    private List<Realm> realmList;

    public DefaultAccessor(List<Realm> realmList) {
        this.realmList = realmList;
    }

    public DefaultAccessor(Realm realm) {
        this.realmList = new ArrayList<>();
        realmList.add(realm);
    }

    @Override
    public boolean isAccessibleForData(Principal principal, Serializable dataId) {
        Collection<String> orgCodes = getAccessibleOrgCodes(principal);
        return false;
    }

    @Override
    public Collection<String> getAccessibleOrgCodes(Principal principal) {
        Collection<String> orgCodes = new HashSet<>();
        for(Realm realm : realmList){
            AccessInfo accessInfo = realm.getAccessInfo(principal);
            orgCodes.addAll(accessInfo.getManageOrgCodes());
        }
        return orgCodes;
    }
}
