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
        assertMatch(authenticateInfo.getCredential(), credential, authenticateInfo.getCredentialSalt());
    }

    public void assertMatch(Object credential, String password, byte[] salt){
        DigestHelper md5Helper = AbstractDigestHelper.getMd5SaltHelper(password.getBytes(), salt);
        if(md5Helper == null || !credential.equals(md5Helper.digestToBase64())){
            throw new CredentialNotMatchException("Credential match failed!");
        }
    }
}
