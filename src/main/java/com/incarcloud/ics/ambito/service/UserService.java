package com.incarcloud.ics.ambito.service;

import com.incarcloud.ics.ambito.entity.ResourceBean;
import com.incarcloud.ics.ambito.entity.UserBean;
import com.incarcloud.ics.ambito.jdbc.BaseService;
import com.incarcloud.ics.core.authc.UsernamePasswordToken;

import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/24
 */
public interface UserService extends BaseService<UserBean> {
    UserBean register(UserBean user);

    List<ResourceBean> getUserMenus(Long userId);

    UserBean login(UsernamePasswordToken usernamePasswordToken);

    void logout();

    UserBean getMyInfo();


//    UserBean detail(Long userId);
}
