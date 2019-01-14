package com.incarcloud.ics.core.org;

import com.incarcloud.ics.core.utils.Asserts;

import java.util.Objects;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
public class SimpleOrganization implements Organization {

    private String code;
    private Organization parent;

    public SimpleOrganization(String code) {
        this(code, null);
    }

    public SimpleOrganization(String code, Organization parent) {
        Asserts.assertNotBlank(code);
        this.code = code;
        this.parent = parent;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public boolean isChief() {
        return this.getParent() == null;
    }

    @Override
    public Organization getParent() {
        return parent;
    }

    @Override
    public boolean isParentOf(Organization o) {
        return o.getCode().startsWith(this.getCode());
    }

    @Override
    public boolean isChildOf(Organization o) {
        return this.getCode().startsWith(o.getCode());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleOrganization)) return false;
        SimpleOrganization that = (SimpleOrganization) o;
        return Objects.equals(getCode(), that.getCode()) &&
                Objects.equals(getParent(), that.getParent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getParent());
    }

    @Override
    public String toString() {
        return "SimpleOrganization{" +
                "code='" + code + '\'' +
                ", parent=" + parent +
                '}';
    }
}
