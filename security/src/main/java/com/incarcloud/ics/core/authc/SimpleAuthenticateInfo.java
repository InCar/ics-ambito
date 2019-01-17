package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.principal.SimplePrincipal;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public class SimpleAuthenticateInfo implements AuthenticateInfo{

    private Principal principal;
    private Object credential;
    private byte[] credentialSalt;

    public SimpleAuthenticateInfo(String username, String password) {
        this(new SimplePrincipal(username), password);
    }

    public SimpleAuthenticateInfo(String username, String password, String salt) {
        this(new SimplePrincipal(username), password, salt.getBytes());
    }

    public SimpleAuthenticateInfo(Principal principal, Object credential) {
        this.principal = principal;
        this.credential = credential;
    }

    public SimpleAuthenticateInfo(Principal principal, Object credential, byte[] credentialSalt) {
        this.principal = principal;
        this.credential = credential;
        this.credentialSalt = credentialSalt;
    }

    @Override
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @Override
    public Object getCredential() {
        return credential;
    }

    public void setCredential(Object credential) {
        this.credential = credential;
    }

    public byte[] getCredentialSalt() {
        return credentialSalt;
    }

    public void setCredentialSalt(byte[] credentialSalt) {
        this.credentialSalt = credentialSalt;
    }
}
