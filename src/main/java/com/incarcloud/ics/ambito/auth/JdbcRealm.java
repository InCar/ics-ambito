package com.incarcloud.ics.ambito.auth;

import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.ResourceBean;
import com.incarcloud.ics.ambito.entity.RoleBean;
import com.incarcloud.ics.ambito.entity.UserBean;
import com.incarcloud.ics.ambito.service.ResourceService;
import com.incarcloud.ics.ambito.service.RoleService;
import com.incarcloud.ics.ambito.service.UserService;
import com.incarcloud.ics.ambito.utils.CollectionUtils;
import com.incarcloud.ics.core.access.AccessInfo;
import com.incarcloud.ics.core.authc.AuthenticateInfo;
import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.authc.SimpleAuthenticateInfo;
import com.incarcloud.ics.core.authz.AuthorizeInfo;
import com.incarcloud.ics.core.exception.AccountNotExistsException;
import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.principal.Principal;
import com.incarcloud.ics.core.realm.AccessRealm;
import com.incarcloud.ics.core.role.SimpleAuthorizeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/9
 */
@Component
public class JdbcRealm extends AccessRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Override
    protected AuthenticateInfo doGetAuthenticateInfo(AuthenticateToken authenticateToken) throws AuthenticationException {
        List<UserBean> users = userService.query(new StringCondition("username", authenticateToken.getPrincipal(), StringCondition.Handler.EQUAL));
        if(users.isEmpty()){
            throw new AccountNotExistsException("No account with username "+ authenticateToken.getPrincipal());
        }
        UserBean userBean = users.get(0);
        return new SimpleAuthenticateInfo(userBean.getUsername(), userBean.getPassword(), userBean.getSalt());
    }

    @Override
    protected AuthorizeInfo doGetAuthorizeInfo(Principal principal) {
        //获取用户的角色信息
        List<UserBean> users = userService.query(new StringCondition("username", principal.getUserIdentity(), StringCondition.Handler.EQUAL));
        if(CollectionUtils.isEmpty(users)){
            return null;
        }
        List<RoleBean> roleBeans = roleService.getRolesOfUser(users.get(0).getId());
        SimpleAuthorizeInfo authorizeInfo = new SimpleAuthorizeInfo();
        authorizeInfo.setRoles(roleBeans.stream().map(e->e.getRoleCode()).collect(Collectors.toSet()));
        List<ResourceBean> resourceBeans = new ArrayList<>();
        for(RoleBean roleBean : roleBeans){
            List<ResourceBean> privilegeOfRole = resourceService.getPrivilegeOfRole(roleBean.getId());
            resourceBeans.addAll(privilegeOfRole);
        }
        authorizeInfo.setStringPrivileges(resourceBeans.stream().map(e->e.getCode()).collect(Collectors.toSet()));
        return authorizeInfo;
    }

    @Override
    protected AccessInfo doGetAccessInfo(Principal principal) {
        return null;
    }

}
