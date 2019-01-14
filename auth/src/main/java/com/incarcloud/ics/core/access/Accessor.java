package com.incarcloud.ics.core.access;


import com.incarcloud.ics.core.Principle.Principal;

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
    boolean isAccessibleForData(Principal principal, Serializable dataId);
    Collection<String> assessibleOrgCodes(Principal principal);
}
