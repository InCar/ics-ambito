package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.realm.Realm;
import com.incarcloud.ics.core.subject.Account;

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
    public Account authenticate(AuthenticateToken authenticateToken, List<Realm> realms) {
        Account userDetail = null;
        for(Realm realm : realms){
            userDetail = realm.getUserDetail(authenticateToken);
            if(userDetail != null){
                break;
            }
        }
        return userDetail;
    }
}
