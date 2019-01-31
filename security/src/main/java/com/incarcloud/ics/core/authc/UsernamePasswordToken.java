package com.incarcloud.ics.core.authc;

import java.io.Serializable;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/3
 */
public class UsernamePasswordToken implements AuthenticateToken,Serializable {

    private static final long serialVersionUID = -7926495708994935591L;

    private String username;

    private String password;

    private boolean isRememberMe;
    private String host;

    public UsernamePasswordToken() {
    }

    public UsernamePasswordToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UsernamePasswordToken(String username, String password, boolean isRememberMe, String host) {
        this.username = username;
        this.password = password;
        this.isRememberMe = isRememberMe;
        this.host = host;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPrincipal() {
        return username;
    }

    @Override
    public String getCredential() {
        return password;
    }

    public boolean isRememberMe() {
        return isRememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        isRememberMe = rememberMe;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
