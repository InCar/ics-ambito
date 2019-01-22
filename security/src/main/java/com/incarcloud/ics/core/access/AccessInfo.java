package com.incarcloud.ics.core.access;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public interface AccessInfo {
    /**
     * 获取用户拥有的数据权限集合
     * @return
     */
    Collection<String> getFilterCodes();

    /**
     * 获取用户拥有管理权限的数据id集合
     * @return
     */
    Map<String,Collection<Serializable>> getAccessibleDataId();
}
