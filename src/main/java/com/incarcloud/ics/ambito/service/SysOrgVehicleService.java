package com.incarcloud.ics.ambito.service;

import com.incarcloud.ics.ambito.entity.SysOrgUserBean;
import com.incarcloud.ics.ambito.entity.SysOrgVehicleBean;
import com.incarcloud.ics.ambito.jdbc.BaseService;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/26 14:24
 */
public interface SysOrgVehicleService extends BaseService<SysOrgVehicleBean> {
    void bind(SysOrgVehicleBean[] sysOrgVehicleBeans);

    void deleteBatch(Long[] ids);
}
