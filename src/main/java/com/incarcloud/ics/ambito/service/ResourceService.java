package com.incarcloud.ics.ambito.service;

import com.incarcloud.ics.ambito.entity.ResourceBean;
import com.incarcloud.ics.ambito.jdbc.BaseService;

import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/27
 */
public interface ResourceService extends BaseService<ResourceBean> {
    List<ResourceBean> getMenusOfRole(long roleId);
    List<ResourceBean> getPrivilegeOfRole(long roleId);
    List<ResourceBean> getResourcesOfRoles(List<Long> roleIds);

    List<ResourceBean> getButtonsOfMenu(String menuCode);
}
