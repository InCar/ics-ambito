package com.incarcloud.ics.ambito.service;

import com.incarcloud.ics.ambito.entity.SysOrgBean;
import com.incarcloud.ics.ambito.jdbc.BaseService;

import java.util.List;
import java.util.Set;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/26 14:24
 */
public interface SysOrgService extends BaseService<SysOrgBean> {
    List<SysOrgBean> getChildrenOrgs(SysOrgBean sysOrgBean);
    List<SysOrgBean> getUserManageOrgs(Long userId);

    List<SysOrgBean> getUserManageOrgs(String username);

    Set<String> getUserManageOrgCodes(String username);
}
