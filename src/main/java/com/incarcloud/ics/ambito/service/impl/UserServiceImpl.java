package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.dao.UserDao;
import com.incarcloud.ics.ambito.entity.UserBean;
import com.incarcloud.ics.ambito.exception.AmbitoException;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.service.UserService;
import com.incarcloud.ics.ambito.utils.CollectionUtils;
import com.incarcloud.ics.ambito.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.logging.Logger;


@Service
public class UserServiceImpl extends BaseServiceImpl<UserBean> implements UserService {

    private static Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
    @Autowired
    private UserDao userDao;

    @Override
    public UserBean register(UserBean user) {
        StringCondition username = new StringCondition("username", user.getUsername(), StringCondition.Handler.EQUAL);
        List<UserBean> userBeans = userDao.query(username);
        if(CollectionUtils.isNotEmpty(userBeans)){
            throw new AmbitoException("用户名重复","1");
        }
        userBeans  = userDao.query(new StringCondition("phone", user.getUsername(), StringCondition.Handler.EQUAL));
        if(CollectionUtils.isNotEmpty(userBeans)){
            throw new AmbitoException("手机号重复","2");
        }
        try {
            String salt = StringUtils.getRandomSecureSalt();
            user.setSalt(salt);
            userDao.save(user);
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            throw new AmbitoException("注册失败","3");
        }
        userBeans = query(username);
        return userBeans.get(0);
    }
}