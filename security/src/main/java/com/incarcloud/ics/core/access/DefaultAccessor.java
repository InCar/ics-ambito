package com.incarcloud.ics.core.access;

import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.realm.Realm;
import com.incarcloud.ics.core.utils.Asserts;

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
        this(new ArrayList<>());
        this.realmList.add(realm);
    }

    @Override
    public boolean isAccessibleForData(Principal principal, Serializable id, Class<?> aClass) {
        Asserts.assertNotNull(aClass, "annotationClass");
        for(Realm realm : realmList){
            if(isAccessibleForRealm(principal, id, aClass, realm)){
                return true;
            }
        }
        return false;
    }

    protected boolean isAccessibleForRealm(Principal principal, Serializable id, Class<?> aClass, Realm realm){
        Asserts.assertNotNull(aClass, "annotationClass");
        AccessInfo accessInfo = realm.getAccessInfo(principal);
        Collection<Serializable> collection = accessInfo.getAccessibleDataId().get(aClass.getName());
        return collection != null && collection.contains(id);
    }

    @Override
    public Collection<String> getFilterCodes(Principal principal, Class<?> aClass) {
        Collection<String> orgCodes = new HashSet<>();
        for(Realm realm : realmList){
            AccessInfo accessInfo = realm.getAccessInfo(principal);
            orgCodes.addAll(accessInfo.getFilterCodes());
        }
        return orgCodes;
    }


}
