package com.incarcloud.ics.core.role;

import com.incarcloud.ics.core.privilege.Privilege;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
public class SimpleRole implements Role , Serializable {

    private static final long serialVersionUID = -7799112276642646040L;
    private String code;
    private Collection<Privilege> privileges;

    public SimpleRole(String code) {
        this.code = code;
        this.privileges = new ArrayList<>();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    @Override
    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleRole)) return false;
        SimpleRole that = (SimpleRole) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimpleRole{");
        sb.append("code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
