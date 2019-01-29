package com.incarcloud.ics.core.authz;

import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.privilege.Privilege;
import com.incarcloud.ics.core.realm.Realm;
import com.incarcloud.ics.core.utils.Asserts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/9
 */
public class DefaultAuthorizer implements Authorizer {

    private List<Realm> realmList = new ArrayList<>();

    public DefaultAuthorizer(Realm realm) {
        Asserts.assertNotNull(realm, "realm");
        realmList.add(realm);
    }

    public DefaultAuthorizer(List<Realm> realmList) {
        this.realmList = realmList;
    }

    @Override
    public boolean isPermitted(Principal principal, Privilege privilege) {
        for(Realm realm : realmList){
            if(!isPermittedOfRealm(realm, principal, privilege)){
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean isPermittedAllStringPrivileges(Principal principal, Collection<String> privileges) {
        for(Realm realm : realmList){
            if(!isPermittedAllStringOfRealm(realm, principal, privileges)){
                return false;
            }
        }
        return true;
    }

    private boolean isPermittedOfRealm(Realm realm, Principal principal, Privilege privilege){
        AuthorizeInfo authorizeInfo = realm.getAuthorizeInfo(principal);
        if(authorizeInfo == null){
            return false;
        }
        for(Privilege pri : authorizeInfo.getPrivileges()){
            if(!pri.implies(privilege)){
                return false;
            }
        }
        return true;
    }

    private boolean isPermittedAllObjectOfRealm(Realm realm, Principal principal, Collection<Privilege> privileges){
        for(Privilege p : privileges){
            if(!isPermittedOfRealm(realm, principal, p)){
                return false;
            }
        }
        return true;
    }


    private boolean isPermittedAllStringOfRealm(Realm realm, Principal principal, Collection<String> privileges){
        AuthorizeInfo authorizeInfo = realm.getAuthorizeInfo(principal);
        if(authorizeInfo == null){
            return false;
        }
        if(!authorizeInfo.getPrivileges().containsAll(privileges)){
            return false;
        }
        return true;
    }


    @Override
    public boolean isPermittedAllObjectPrivileges(Principal account, Collection<Privilege> privileges) {
        for(Realm realm : realmList){
            if(!isPermittedAllObjectOfRealm(realm, account, privileges)){
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean hasRole(Principal account, String role) {
        for(Realm realm : realmList){
            if(!realmHasRole(realm, account, role)){
                return false;
            }
        }
        return true;
    }

    private boolean realmHasRole(Realm realm, Principal account, String role) {
        AuthorizeInfo authorizeInfo = realm.getAuthorizeInfo(account);
        if(authorizeInfo == null){
            return false;
        }else {
            return authorizeInfo.getRoles().contains(role);
        }
    }


    @Override
    public boolean hasAllRoles(Principal account, Collection<String> roleList) {
        for(Realm realm : realmList){
            if(!realmHasAllRole(realm, account, roleList)){
                return false;
            }
        }
        return true;
    }

    private boolean realmHasAllRole(Realm realm, Principal account, Collection<String> roles) {
        AuthorizeInfo authorizeInfo = realm.getAuthorizeInfo(account);
        if(authorizeInfo == null){
            return false;
        }else {
            return authorizeInfo.getRoles().containsAll(roles);
        }
    }
}
