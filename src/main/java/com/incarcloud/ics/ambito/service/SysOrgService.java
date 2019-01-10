package com.incarcloud.ics.ambito.service;

import com.incarcloud.ics.ambito.entity.SysOrgBean;
import com.incarcloud.ics.ambito.jdbc.BaseService;

import java.util.List;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/26 14:24
 */
public interface SysOrgService extends BaseService<SysOrgBean> {
    List<SysOrgBean> getChildrenOrgs(SysOrgBean sysOrgBean);
}
