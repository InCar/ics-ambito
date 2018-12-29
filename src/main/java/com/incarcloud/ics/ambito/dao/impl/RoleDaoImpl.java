package com.incarcloud.ics.ambito.dao.impl;

import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.dao.RoleDao;
import com.incarcloud.ics.ambito.entity.RoleBean;
import com.incarcloud.ics.ambito.entity.UserRoleBean;
import com.incarcloud.ics.ambito.jdbc.BaseDaoImpl;
import com.incarcloud.ics.ambito.service.RoleService;
import com.incarcloud.ics.ambito.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/25
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<RoleBean> implements RoleDao {


}
