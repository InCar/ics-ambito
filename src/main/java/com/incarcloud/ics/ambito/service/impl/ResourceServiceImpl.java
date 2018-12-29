package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.entity.ResourceBean;
import com.incarcloud.ics.ambito.entity.RoleBean;
import com.incarcloud.ics.ambito.entity.RoleResourceBean;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.service.ResourceService;
import com.incarcloud.ics.ambito.service.RoleResourceService;
import com.incarcloud.ics.ambito.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/27
 */
@Service
public class ResourceServiceImpl extends BaseServiceImpl<ResourceBean> implements ResourceService {


    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private ResourceService resourceService;

    /**
     * 获取角色所有的菜单
     * @param roleId
     * @return
     */
    @Override
    public List<ResourceBean> getMenusOfRole(long roleId) {
        List<RoleResourceBean> roleResourceBeans = roleResourceService.query(new NumberCondition("roleId", roleId, NumberCondition.Handler.EQUAL));
        List<Long> resources = roleResourceBeans.stream().map(RoleResourceBean::getResourceId).collect(Collectors.toList());
        List<ResourceBean> menus = resourceService.query(Condition.and(
                new NumberCondition("id", resources, NumberCondition.Handler.IN),
                new NumberCondition("resourceType", 0, NumberCondition.Handler.EQUAL)
        ));
        return menus;
    }


    /**
     * 获取角色所有的权限
     * @param roleId
     * @return
     */
    @Override
    public List<ResourceBean> getPrivilegeOfRole(long roleId) {
        List<RoleResourceBean> roleResourceBeans = roleResourceService.query(new NumberCondition("roleId", roleId, NumberCondition.Handler.EQUAL));
        List<Long> resources = roleResourceBeans.stream().map(RoleResourceBean::getResourceId).collect(Collectors.toList());
        List<ResourceBean> privleges = resourceService.query(Condition.and(
                new NumberCondition("id", resources, NumberCondition.Handler.IN),
                new NumberCondition("resourceType", 1, NumberCondition.Handler.EQUAL)
        ));
        return privleges;
    }


    /**
     * 获取角色所有的权限
     * @param roleIds
     * @return
     */
    @Override
    public List<ResourceBean> getResourcesOfRoles(List<Long> roleIds) {
        List<RoleResourceBean> roleResourceBeans = roleResourceService.query(new NumberCondition("roleId", roleIds, NumberCondition.Handler.IN));
        List<Long> resources = roleResourceBeans.stream().map(RoleResourceBean::getResourceId).collect(Collectors.toList());
        return resourceService.query(Condition.and(
                new NumberCondition("id", resources, NumberCondition.Handler.IN)
        ));
    }


}
