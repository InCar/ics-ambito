package com.incarcloud.ics.ambito.jdbc;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/3/4
 */
public interface AuthDataClearable {
    void clearCachedAuthData();
    boolean isClearRequired();
}
