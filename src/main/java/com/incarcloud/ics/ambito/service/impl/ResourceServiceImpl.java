package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.ResourceBean;
import com.incarcloud.ics.ambito.entity.RoleResourceBean;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.service.ResourceService;
import com.incarcloud.ics.ambito.service.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    /**
     * 获取角色所有的菜单
     * @param roleId
     * @return
     */
    @Override
    public List<ResourceBean> getMenusOfRole(long roleId) {
        List<RoleResourceBean> roleResourceBeans = roleResourceService.query(new NumberCondition("roleId", roleId, NumberCondition.Handler.EQUAL));
        List<Long> resources = roleResourceBeans.stream().map(RoleResourceBean::getResourceId).collect(Collectors.toList());
        List<ResourceBean> menus = this.query(Condition.and(
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
        List<ResourceBean> privleges = this.query(Condition.and(
                new NumberCondition("id", resources, NumberCondition.Handler.IN),
                new NumberCondition("type", 1, NumberCondition.Handler.EQUAL)
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
        return this.query(Condition.and(
                new NumberCondition("id", resources, NumberCondition.Handler.IN)
        ));
    }


    /**
     * 获取某个菜单下的所有按钮
     * @param menuCode
     * @return
     */
    @Override
    public List<ResourceBean> getButtonsOfMenu(String menuCode) {
        List<ResourceBean> resourceBeans = this.query(new NumberCondition("code", menuCode));
        if(resourceBeans.isEmpty()){
            return Collections.emptyList();
        }
        ResourceBean menu = resourceBeans.get(0);

        List<ResourceBean> btnBeans = this.query(Condition.and(
                new StringCondition("parentCodes", menu.getParentCodes(), StringCondition.Handler.RIGHT_LIKE),
                new NumberCondition("type", ResourceBean.BUTTON)
            )
        );
        return btnBeans;
    }
}
