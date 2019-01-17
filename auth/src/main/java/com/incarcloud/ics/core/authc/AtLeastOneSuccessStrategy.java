package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.realm.Realm;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public class AtLeastOneSuccessStrategy implements AuthenticateStrategy {

    private Logger logger = Logger.getLogger(AtLeastOneSuccessStrategy.class.getName());

    public AtLeastOneSuccessStrategy() {
    }


    @Override
    public AuthenticateInfo authenticate(AuthenticateToken authenticateToken, List<Realm> realms) {
        AuthenticateInfo userDetail = null;
        for(Realm realm : realms){
            userDetail = realm.getAuthenticateInfo(authenticateToken);
            if(userDetail != null){
                break;
            }
        }
        return userDetail;
    }
}
