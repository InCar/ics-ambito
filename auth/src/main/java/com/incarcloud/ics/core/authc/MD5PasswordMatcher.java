package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.crypo.AbstractDigestHelper;
import com.incarcloud.ics.core.crypo.DigestHelper;
import com.incarcloud.ics.core.exception.CredentialNotMatchException;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public class MD5PasswordMatcher implements CredentialMatcher {


    @Override
    public void assertMatch(AuthenticateInfo authenticateInfo, String credential) {
        DigestHelper md5Helper = AbstractDigestHelper.getMd5SaltHelper(credential.getBytes(), authenticateInfo.getCredentialSalt());
        if(md5Helper == null){
            throw new CredentialNotMatchException("No digest helper configured");
        }
        if(!authenticateInfo.getCredential().equals(md5Helper.toBase64())){
            throw new CredentialNotMatchException("Credential match failed!");
        }
    }
}
