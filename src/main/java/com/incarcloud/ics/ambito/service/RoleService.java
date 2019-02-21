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

    RoleBean saveOrUpdate(RoleBean roleBean);

    Object getList(Long id, String roleName, Integer page, Integer pageSize);
}
