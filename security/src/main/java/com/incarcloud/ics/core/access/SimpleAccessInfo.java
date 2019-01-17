package com.incarcloud.ics.core.access;

import java.util.Collection;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/17
 */
public class SimpleAccessInfo implements AccessInfo {

    private Collection<String> manageOrgCodes;

    public SimpleAccessInfo(Collection<String> manageOrgCodes) {
        this.manageOrgCodes = manageOrgCodes;
    }

    @Override
    public Collection<String> getManageOrgCodes() {
        return manageOrgCodes;
    }

    public void setManageOrgCodes(Collection<String> manageOrgCodes) {
        this.manageOrgCodes = manageOrgCodes;
    }
}
