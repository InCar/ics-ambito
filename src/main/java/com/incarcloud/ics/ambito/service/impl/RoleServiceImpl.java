package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.RoleBean;
import com.incarcloud.ics.ambito.entity.UserRoleBean;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.pojo.Page;
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

    @Override
    public RoleBean saveOrUpdate(RoleBean roleBean) {
        if(roleBean.getId() == null){
            this.save(roleBean);
        }else {
            this.update(roleBean);
        }
        List<RoleBean> roleBeans = this.query(new StringCondition("roleCode", roleBean.getRoleCode()));
        return roleBeans.get(0);
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
