package com.incarcloud.ics.core.privilege;

import java.io.Serializable;
import java.util.Objects;

public class SimplePrivilege implements Privilege, Serializable {
    private static final long serialVersionUID = 5534486291693797764L;

    private String identifier;

    public SimplePrivilege(String identifier) {
        this.identifier = identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCode(){
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimplePrivilege)) return false;
        SimplePrivilege that = (SimplePrivilege) o;
        return Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }
}
