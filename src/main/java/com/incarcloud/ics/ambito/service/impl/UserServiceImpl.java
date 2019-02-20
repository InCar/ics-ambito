package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.dao.UserDao;
import com.incarcloud.ics.ambito.entity.*;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.ResourceService;
import com.incarcloud.ics.ambito.service.RoleService;
import com.incarcloud.ics.ambito.service.SysOrgUserService;
import com.incarcloud.ics.ambito.service.UserService;
import com.incarcloud.ics.ambito.utils.CollectionUtils;
import com.incarcloud.ics.ambito.utils.StringUtils;
import com.incarcloud.ics.core.authc.UsernamePasswordToken;
import com.incarcloud.ics.core.crypo.AbstractDigestHelper;
import com.incarcloud.ics.core.crypo.DigestHelper;
import com.incarcloud.ics.core.security.SecurityUtils;
import com.incarcloud.ics.core.session.Session;
import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.log.Logger;
import com.incarcloud.ics.log.LoggerFactory;
import com.incarcloud.ics.pojo.ErrorDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
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
        existing.setSalt(null);
        existing.setPassword(null);
        return existing;
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
        Session session = subject.getSession();
        List<UserBean> userBeans = query(new StringCondition("username", usernamePasswordToken.getPrincipal(), StringCondition.Handler.EQUAL));
        session.setAttribute("myInfo", userBeans.get(0));
        return userBeans.get(0);
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @Override
    public UserBean getMyInfo() {
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            throw ErrorDefine.UN_AUTHENTICATED.toAmbitoException();
        }
        Session session = subject.getSession();
        return (UserBean) session.getAttribute("myInfo");
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
            ((Collection<UserBean>)o).forEach(e->{
                e.setSalt(null);
                e.setPassword(null);
            });
        }
        return o;
    }

}