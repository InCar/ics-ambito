package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.crypo.AbstractDegiestHelper;
import com.incarcloud.ics.core.crypo.DigestHelper;
import com.incarcloud.ics.core.exception.CredentialNotMatchException;
import com.incarcloud.ics.core.subject.Account;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public class MD5PasswordMatcher implements CredentialMatcher {


    @Override
    public void assertMatch(Account authenticateInfo, String credential) {
        DigestHelper md5Helper = AbstractDegiestHelper.getMd5SaltHelper(credential.getBytes(), authenticateInfo.getCredentialsSalt());
        if(md5Helper == null){
            throw new CredentialNotMatchException("No digest helper configured");
        }
        if(!authenticateInfo.getCredential().equals(md5Helper.toBase64())){
            throw new CredentialNotMatchException("Credential match failed!");
        }
    }
}
