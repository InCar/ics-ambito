package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.common.ErrorDefine;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.dao.UserDao;
import com.incarcloud.ics.ambito.entity.BaseBean;
import com.incarcloud.ics.ambito.entity.ResourceBean;
import com.incarcloud.ics.ambito.entity.RoleBean;
import com.incarcloud.ics.ambito.entity.UserBean;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.service.ResourceService;
import com.incarcloud.ics.ambito.service.RoleService;
import com.incarcloud.ics.ambito.service.UserService;
import com.incarcloud.ics.ambito.utils.CollectionUtils;
import com.incarcloud.ics.ambito.utils.StringUtils;
import com.incarcloud.ics.core.crypo.AbstractDegiestHelper;
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
        //生成随机盐
        String salt = StringUtils.getRandomSecureSalt();
        //使用随机盐混合密码进行md5加密
        DigestHelper digestHelper = AbstractDegiestHelper.getMd5SaltHelper(user.getPassword().getBytes(), salt.getBytes());
        user.setSalt(salt);
        //保存加密后的密码，格式为base64
        user.setPassword(digestHelper.toBase64());
        userDao.save(user);
        userBeans = query(username);
        return userBeans.get(0);
    }

    @Override
    public List<ResourceBean> getUserMenus(Long userId) {
        List<RoleBean> rolesOfUser = roleService.getRolesOfUser(userId);
        List<Long> roles = rolesOfUser.stream().map(BaseBean::getId).collect(Collectors.toList());
        return resourceService.getResourcesOfRoles(roles);
    }

}