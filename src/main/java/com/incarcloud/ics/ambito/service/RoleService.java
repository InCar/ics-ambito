package com.incarcloud.ics.ambito.service;

import com.incarcloud.ics.ambito.entity.RoleBean;
import com.incarcloud.ics.ambito.jdbc.BaseService;

import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/27
 */
public interface RoleService extends BaseService<RoleBean> {
    List<RoleBean> getRolesOfUser(Long userId);
}
