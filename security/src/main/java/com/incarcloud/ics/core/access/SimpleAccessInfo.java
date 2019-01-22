package com.incarcloud.ics.core.access;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/17
 */
public class SimpleAccessInfo implements AccessInfo {

    private Collection<String> filterCodes;
    private Map<String,Collection<Serializable>> accessibleDataId;

    public SimpleAccessInfo() {
        this.filterCodes = new ArrayList<>();
        this.accessibleDataId = new HashMap<>();
    }

    public SimpleAccessInfo(Collection<String> accessibleCodes, Map<String, Collection<Serializable>> accessibleDataId) {
        this.filterCodes = accessibleCodes;
        this.accessibleDataId = accessibleDataId;
    }

    @Override
    public Collection<String> getFilterCodes() {
        return filterCodes;
    }

    public void setFilterCodes(Collection<String> filterCodes) {
        this.filterCodes = filterCodes;
    }

    @Override
    public Map<String, Collection<Serializable>> getAccessibleDataId() {
        return accessibleDataId;
    }

    public void setAccessibleDataId(Map<String, Collection<Serializable>> accessibleDataId) {
        this.accessibleDataId = accessibleDataId;
    }
}
