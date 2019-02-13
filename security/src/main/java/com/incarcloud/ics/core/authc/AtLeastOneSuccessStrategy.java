package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.realm.Realm;
import com.incarcloud.ics.log.Logger;
import com.incarcloud.ics.log.LoggerFactory;

import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public class AtLeastOneSuccessStrategy implements AuthenticateStrategy {

    private Logger logger = LoggerFactory.getLogger(AtLeastOneSuccessStrategy.class);

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
