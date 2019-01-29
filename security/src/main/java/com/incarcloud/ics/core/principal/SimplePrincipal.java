package com.incarcloud.ics.core.principal;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/2
 */
public class SimplePrincipal implements Principal, Serializable {
    private static final long serialVersionUID = 7853511316357199871L;
    private String userIdentify;

    public SimplePrincipal(String principal) {
        this.userIdentify = principal;
    }

    @Override
    public String getUserIdentity() {
        return userIdentify;
    }


    public void setUserIdentify(String userIdentify) {
        this.userIdentify = userIdentify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimplePrincipal)) return false;
        SimplePrincipal that = (SimplePrincipal) o;
        return Objects.equals(getUserIdentity(), that.getUserIdentity());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUserIdentity());
    }
}
