package com.incarcloud.ics.core.access;


import com.incarcloud.ics.core.principal.Principal;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description
 * 数据权限验证接口
 *
 * @Author ThomasChan
 * @Date 2018/12/19 5:13 PM
 * @Version 1.0
 */
public interface Accessor {

    boolean isAccessibleForData(Principal principal, Serializable dataId, Class<?> aClass);

    Collection<String> getFilterCodes(Principal principal, Class<?> aClass);
}
