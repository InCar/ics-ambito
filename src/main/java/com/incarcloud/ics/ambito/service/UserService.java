package com.incarcloud.ics.ambito.service;

import com.incarcloud.ics.ambito.entity.ResourceBean;
import com.incarcloud.ics.ambito.entity.UserBean;
import com.incarcloud.ics.ambito.jdbc.BaseService;
import com.incarcloud.ics.core.authc.UsernamePasswordToken;

import java.util.List;
import java.util.Map;

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

    UserBean maskCredential(UserBean userBean);

    Object query(Long id, String username, String phone, String realName, String createUser, Integer pageNum, Integer pageSize);

    void updatePassword(Map<String, String> userInfo);
}
