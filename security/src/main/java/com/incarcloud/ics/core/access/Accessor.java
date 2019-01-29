package com.incarcloud.ics.core.access;


import com.incarcloud.ics.core.principal.Principal;

import java.io.Serializable;
import java.util.Collection;

/**
 * @description
 * 数据权限验证接口
 *
 * @author ThomasChan
 * @date 2018/12/19 5:13 PM
 * @version 1.0
 */
public interface Accessor {

    boolean isAccessibleForData(Principal principal, Serializable dataId, Class<?> aClass);

    Collection<String> getFilterCodes(Principal principal, Class<?> aClass);
}
