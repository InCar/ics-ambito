package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.principal.Principal;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public interface AuthenticateInfo {
    Principal getPrincipal();
    Object getCredential();
    byte[] getCredentialSalt();
}
