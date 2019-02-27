package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.dao.UserDao;
import com.incarcloud.ics.ambito.entity.*;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.security.AuthUtils;
import com.incarcloud.ics.ambito.service.ResourceService;
import com.incarcloud.ics.ambito.service.RoleService;
import com.incarcloud.ics.ambito.service.SysOrgUserService;
import com.incarcloud.ics.ambito.service.UserService;
import com.incarcloud.ics.ambito.utils.CollectionUtils;
import com.incarcloud.ics.ambito.utils.StringUtils;
import com.incarcloud.ics.core.authc.MD5PasswordMatcher;
import com.incarcloud.ics.core.authc.UsernamePasswordToken;
import com.incarcloud.ics.core.crypo.AbstractDigestHelper;
import com.incarcloud.ics.core.crypo.DigestHelper;
import com.incarcloud.ics.core.principal.SimplePrincipal;
import com.incarcloud.ics.core.security.SecurityUtils;
import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.log.Logger;
import com.incarcloud.ics.log.LoggerFactory;
import com.incarcloud.ics.pojo.ErrorDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl extends BaseServiceImpl<UserBean> implements UserService {

    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private SysOrgUserService sysOrgUserService;

    @Override
    public UserBean register(UserBean user) {
        //生成随机salt
        String salt = StringUtils.getRandomSecureSalt();
        //使用随机salt混合密码进行md5加密
        DigestHelper digestHelper = AbstractDigestHelper.getMd5SaltHelper(user.getPassword().getBytes(), salt.getBytes());
        user.setSalt(salt);
        //保存加密后的密码，格式为base64
        user.setPassword(digestHelper.digestToBase64());
        userDao.save(user);

        StringCondition username = new StringCondition("username", user.getUsername(), StringCondition.Handler.EQUAL);
        List<UserBean> userBeans = query(username);
        UserBean existing = userBeans.get(0);
        //保存用户所属组织
        List<Long> orgs = user.getOrgIds();
        if(CollectionUtils.isNotEmpty(orgs)){
            orgs.forEach(e->{
                sysOrgUserService.save(new SysOrgUserBean(e, existing.getId()));
            });
        }
        return maskCredential(existing);
    }

    @Override
    public List<ResourceBean> getUserMenus(Long userId) {
        List<RoleBean> rolesOfUser = roleService.getRolesOfUser(userId);
        List<Long> roles = rolesOfUser.stream().map(BaseBean::getId).collect(Collectors.toList());
        return resourceService.getResourcesOfRoles(roles);
    }

    @Override
    public UserBean login(UsernamePasswordToken usernamePasswordToken) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        List<UserBean> userBeans = query(new StringCondition("username", usernamePasswordToken.getPrincipal(), StringCondition.Handler.EQUAL));
        UserBean userBean = AuthUtils.storeCurrentUser(userBeans.get(0));
        return maskCredential(userBean);
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @Override
    public UserBean getMyInfo() {
        UserBean currentUser = AuthUtils.getCurrentUser();
        if(currentUser != null){
            maskCredential(currentUser);
        }
        return currentUser;
    }

    @Override
    public UserBean maskCredential(UserBean userBean){
        userBean.setSalt(null);
        userBean.setPassword(null);
        return userBean;
    }


    @Override
    @SuppressWarnings("unchecked")
    public Object query(Long id, String username, String phone, String realName, String createUser, Integer pageNum, Integer pageSize) {
        Condition cond = Condition.and(
                new NumberCondition("id", id, NumberCondition.Handler.EQUAL),
                new StringCondition("username", username, StringCondition.Handler.ALL_LIKE),
                new StringCondition("createUser", createUser, StringCondition.Handler.ALL_LIKE),
                new StringCondition("realName", realName, StringCondition.Handler.ALL_LIKE),
                new StringCondition("phone", phone, StringCondition.Handler.ALL_LIKE)
        );
        Object o = null;
        if(pageNum == null || pageSize == null){
            o = query(cond);
        }else {
            o = queryPage(new Page(pageNum, pageSize), cond);
        }
        if(o instanceof Collection){
            ((Collection<UserBean>)o).forEach(this::maskCredential);
        }
        return o;
    }

    @Override
    public void updatePassword(Map<String, String> userInfo) {
        String username = userInfo.get("username");
        List<UserBean> userBeans = this.query(new StringCondition("username", username));
        UserBean userBean = userBeans.get(0);
        String oldPassword = userInfo.get("password");
        MD5PasswordMatcher matcher = new MD5PasswordMatcher();
        if(!matcher.isMatch(userBean.getPassword(), oldPassword, userBean.getSalt().getBytes())){
            throw ErrorDefine.ORIGIN_PASSWORD_NOT_MATCH.toAmbitoException();
        }
        String newPassword = userInfo.get("newPassword");
        String newSalt = StringUtils.getRandomSecureSalt();
        DigestHelper digestHelper = AbstractDigestHelper.getMd5SaltHelper(newPassword.getBytes(),newSalt.getBytes());
        userBean.setPassword(digestHelper.digestToBase64());
        userBean.setSalt(newSalt);
        this.update(userBean);
        AuthUtils.clearUserAuthCache(new SimplePrincipal(username));
        //hhDT8lNj7jRew1PcvVPRvQ==
    }

}