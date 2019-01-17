package com.incarcloud.ics.core.org;

import java.util.Collection;
import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/16
 */
public interface OrganizationResolver {
    /**
     * 从提供的组织集合获取所属组织
     * @param orgs
     * @return
     */
    List<Organization> getManageOrganization(Collection orgs);
}
