package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.ResourceBean;
import com.incarcloud.ics.ambito.entity.RoleResourceBean;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.ResourceService;
import com.incarcloud.ics.ambito.service.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
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
        if(resources.isEmpty()){
            return Collections.emptyList();
        }
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
        if(resources.isEmpty()) {
            return Collections.emptyList();
        }
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
        if(roleIds.isEmpty()){
            return Collections.emptyList();
        }
        List<RoleResourceBean> roleResourceBeans = roleResourceService.query(new NumberCondition("roleId", roleIds, NumberCondition.Handler.IN));
        List<Long> resources = roleResourceBeans.stream().map(RoleResourceBean::getResourceId).collect(Collectors.toList());
        return this.query(Condition.and(
                new NumberCondition("id", resources, NumberCondition.Handler.IN)
        ));
    }


    /**
     * 获取某个菜单下的所有按钮
     * @param id
     * @return
     */
    @Override
    public List<ResourceBean> getButtonsOfMenu(Long id) {
        ResourceBean menu = this.get(id);
        if(menu == null){
            return Collections.emptyList();
        }
        List<ResourceBean> btnBeans = this.query(Condition.and(
                new StringCondition("parentIds", menu.getParentIds(), StringCondition.Handler.RIGHT_LIKE),
                new StringCondition("type", ResourceBean.ResourceType.menu)
            )
        );
        return btnBeans;
    }

    @Override
    public Object getList(Long id, String resourceName, Integer page, Integer pageSize) {
        Condition cond = Condition.and(
                new StringCondition("resourceName", resourceName, StringCondition.Handler.ALL_LIKE),
                new NumberCondition("id", id, NumberCondition.Handler.EQUAL)
        );
        if(page == null || pageSize == null){
            return this.query(cond);
        }else {
            return this.queryPage(new Page(page, pageSize), cond);
        }
    }

    @Override
    public ResourceBean saveOrUpdate(ResourceBean resourceBean) {
        if(resourceBean.getId() == null){
            this.save(preHandle(resourceBean));
        }else {
            this.update(resourceBean);
        }
        List<ResourceBean> newBeans = this.query(new StringCondition("resourceName", resourceBean.getResourceName()));
        return newBeans.get(0);
    }

    private ResourceBean preHandle(ResourceBean resourceBean) {
        if(resourceBean.getParentId() == null || resourceBean.getParentId() == 0){
            resourceBean.setParentId(0L);
            resourceBean.setLevel((byte)0);
        }else {
            ResourceBean parent = this.get(resourceBean.getParentId());
            resourceBean.setLevel((byte) (parent.getLevel()+1));
        }
        return resourceBean;
    }


    @Override
    @Transactional
    public int delete(Serializable id) {
        ResourceBean resourceBean = this.get(id);
        if(resourceBean.getType().equals(ResourceBean.ResourceType.menu)){
            List<ResourceBean> children = this.query(Condition.and(
                    new NumberCondition("type", ResourceBean.ResourceType.menu),
                    new NumberCondition("parentId", id),
                    new StringCondition("parentIds", resourceBean.getParentIds(), StringCondition.Handler.RIGHT_LIKE)
            ));
            //删除所有子资源
            children.forEach(e->super.delete(e.getId()));
            super.delete(id);
            return children.size() + 1;
        }else {
            super.delete(id);
        }
        return 1;
    }
}
