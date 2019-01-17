package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.common.ErrorDefine;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.dao.UserDao;
import com.incarcloud.ics.ambito.entity.*;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.service.ResourceService;
import com.incarcloud.ics.ambito.service.RoleService;
import com.incarcloud.ics.ambito.service.SysOrgUserService;
import com.incarcloud.ics.ambito.service.UserService;
import com.incarcloud.ics.ambito.utils.CollectionUtils;
import com.incarcloud.ics.ambito.utils.StringUtils;
import com.incarcloud.ics.core.crypo.AbstractDigestHelper;
import com.incarcloud.ics.core.crypo.DigestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl extends BaseServiceImpl<UserBean> implements UserService {

    private static Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

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
        StringCondition username = new StringCondition("username", user.getUsername(), StringCondition.Handler.EQUAL);
        List<UserBean> userBeans = userDao.query(username);
        if(CollectionUtils.isNotEmpty(userBeans)){
            throw ErrorDefine.REPEATED_USERNAME.toAmbitoException();
        }
        userBeans  = userDao.query(new StringCondition("phone", user.getPhone(), StringCondition.Handler.EQUAL));
        if(CollectionUtils.isNotEmpty(userBeans)){
            throw ErrorDefine.REPEATED_PHONE.toAmbitoException();
        }
        //生成随机salt
        String salt = StringUtils.getRandomSecureSalt();
        //使用随机salt混合密码进行md5加密
        DigestHelper digestHelper = AbstractDigestHelper.getMd5SaltHelper(user.getPassword().getBytes(), salt.getBytes());
        user.setSalt(salt);
        //保存加密后的密码，格式为base64
        user.setPassword(digestHelper.digestToBase64());
        userDao.save(user);
        userBeans = query(username);
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

}