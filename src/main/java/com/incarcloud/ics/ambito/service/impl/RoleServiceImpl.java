package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.RoleBean;
import com.incarcloud.ics.ambito.entity.RoleResourceBean;
import com.incarcloud.ics.ambito.entity.UserRoleBean;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.RoleResourceService;
import com.incarcloud.ics.ambito.service.RoleService;
import com.incarcloud.ics.ambito.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/27
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleBean> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Override
    public List<RoleBean> getRolesOfUser(Long userId) {
        List<UserRoleBean> userRoles = userRoleService.query(new NumberCondition("userId", userId));
        List<Long> roleIds = userRoles.stream().map(UserRoleBean::getRoleId).collect(Collectors.toList());
        return this.query(new NumberCondition("id", roleIds, NumberCondition.Handler.IN));
    }

    @Override
    @Transactional
    public RoleBean saveOrUpdate(RoleBean roleBean) {
        if(roleBean.getId() == null){
            this.save(roleBean);
        }else {
            this.update(roleBean);
        }
        List<RoleBean> roleBeans = this.query(new StringCondition("roleCode", roleBean.getRoleCode()));
        RoleBean newRole = roleBeans.get(0);
        newRole.setUserIds(roleBean.getUserIds());
        newRole.setResourceIds(roleBean.getResourceIds());
        updateRelative(newRole);
        return newRole;
    }

    private void updateRelative(RoleBean roleBean){
        roleResourceService.delete(new NumberCondition("roleId", roleBean.getId()));
        userRoleService.delete(new NumberCondition("roleId", roleBean.getId()));
        if(roleBean.getResourceIds() != null){
            roleBean.getResourceIds().forEach(e -> roleResourceService.save(new RoleResourceBean(e, roleBean.getId())));
        }
        if(roleBean.getUserIds() != null){
            roleBean.getUserIds().forEach(e -> userRoleService.save(new UserRoleBean(e, roleBean.getId())));
        }
    }

    @Override
    public Object getList(Long id, String roleName, Integer page, Integer pageSize) {
        Condition cond = Condition.and(
                new StringCondition("roleName", roleName, StringCondition.Handler.ALL_LIKE),
                new NumberCondition("id", id)
        );
        if(page == null || pageSize == null){
            return this.query(cond);
        }else {
            return this.queryPage(new Page(page, pageSize), cond);
        }
    }
}
