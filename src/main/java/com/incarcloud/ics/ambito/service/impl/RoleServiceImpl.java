package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.entity.RoleBean;
import com.incarcloud.ics.ambito.entity.UserRoleBean;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.service.RoleService;
import com.incarcloud.ics.ambito.service.UserRoleService;
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
public class RoleServiceImpl extends BaseServiceImpl<RoleBean> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<RoleBean> getRolesOfUser(Long userId) {
        List<UserRoleBean> userRoles = userRoleService.query(new NumberCondition("userId", userId));
        List<Long> roleIds = userRoles.stream().map(UserRoleBean::getRoleId).collect(Collectors.toList());
        return this.query(new NumberCondition("id", roleIds, NumberCondition.Handler.IN));
    }
}
