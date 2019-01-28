package com.incarcloud.ics.core.authc;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/3
 */
public interface AuthenticateToken {

    String getPrincipal();

    String getCredential();
}
