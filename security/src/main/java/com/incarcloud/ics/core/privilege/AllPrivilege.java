package com.incarcloud.ics.core.privilege;

public class AllPrivilege implements Privilege {
    @Override
    public boolean implies(Privilege privilege) {
        return true;
    }
}
