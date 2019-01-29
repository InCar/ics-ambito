package com.incarcloud.ics.core.privilege;

public interface Privilege {
    boolean implies(Privilege privilege);
}
