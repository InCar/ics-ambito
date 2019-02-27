package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.crypo.AbstractDigestHelper;
import com.incarcloud.ics.core.crypo.DigestHelper;
import com.incarcloud.ics.core.exception.CredentialNotMatchException;
import com.incarcloud.ics.log.Logger;
import com.incarcloud.ics.log.LoggerFactory;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public class MD5PasswordMatcher implements CredentialMatcher {

    private static Logger logger = LoggerFactory.getLogger(MD5PasswordMatcher.class);

    @Override
    public void assertMatch(AuthenticateInfo authenticateInfo, String credential) {
        boolean isMatch = isMatch(authenticateInfo.getCredential(), credential, authenticateInfo.getCredentialSalt());
        if(!isMatch){
            throw new CredentialNotMatchException("Credential is not match");
        }
    }

    public boolean isMatch(Object credential, String password, byte[] salt){
        DigestHelper md5Helper = AbstractDigestHelper.getMd5SaltHelper(password.getBytes(), salt);
        if(md5Helper == null){
            logger.debug("Md5Helper is null");
        }
        return md5Helper != null && credential.equals(md5Helper.digestToBase64());
    }
}
