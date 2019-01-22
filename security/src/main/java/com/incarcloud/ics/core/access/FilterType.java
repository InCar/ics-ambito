package com.incarcloud.ics.core.access;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/21 2:42 PM
 */
public enum FilterType {
    ORGANIZATION("orgCode"),

    ROLE("roleCode"),

    VEHICLE("vinCode");

    String columnName;

    FilterType(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
