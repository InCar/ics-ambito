package com.incarcloud.ics.ambito.auth;

import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.RoleBean;
import com.incarcloud.ics.ambito.entity.UserBean;
import com.incarcloud.ics.ambito.service.RoleService;
import com.incarcloud.ics.ambito.service.UserService;
import com.incarcloud.ics.core.principal.SimplePrincipal;
import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.authc.UsernamePasswordToken;
import com.incarcloud.ics.core.exception.AccountNotExistsException;
import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.realm.SimpleAccountRealm;
import com.incarcloud.ics.core.role.SimpleRole;
import com.incarcloud.ics.core.subject.Account;
import com.incarcloud.ics.core.subject.SimpleAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/9
 */
@Component
public class JdbcRealm extends SimpleAccountRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    protected Account doGetUserDetail(AuthenticateToken token) throws AuthenticationException {
        UsernamePasswordToken userpasswordToken = (UsernamePasswordToken)token;
        Account account = getAccountInfo(token.getPrincipal());
        if(account == null){
            List<UserBean> users = userService.query(new StringCondition("username", userpasswordToken.getPrincipal(), StringCondition.Handler.EQUAL));
            if(users.isEmpty()){
                throw new AccountNotExistsException("No account with username "+ token.getPrincipal());
            }
            UserBean userBean = users.get(0);
            SimplePrincipal simplePrincipal = new SimplePrincipal(userBean.getUsername());
            account = new SimpleAccount(simplePrincipal, userBean.getPassword(), userBean.getSalt().getBytes());
            //获取用户的角色信息
            List<RoleBean> roleBeans = roleService.getRolesOfUser(userBean.getId());
            account.setRoles(roleBeans.stream().map(e->new SimpleRole(e.getRoleCode())).collect(Collectors.toList()));
            addAccount(account);
        }
        return account;
    }




}
