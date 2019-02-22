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

    List<SysOrgBean> getDirectChildrenOrgs(SysOrgBean sysOrgBean);

    Set<SysOrgBean> getUserManageOrgs(Long userId);

    Set<SysOrgBean> getUserManageOrgs(String username);

    Set<String> getUserManageOrgCodes(String username);

    Set<String> getUserBelongOrgCodes(String username);

    Set<String> getAllOrgCodes();

    Object getList(Long id, String orgName, String parentId, Integer pageNum, Integer pageSize);

    SysOrgBean saveOrUpdate(SysOrgBean sysOrgBean);

    SysOrgBean getOrgTree(Long id);
}
