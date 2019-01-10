package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.entity.SysOrgVehicleBean;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.service.SysOrgVehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/26 14:29
 */
@Service
public class SysOrgVehicleServiceImpl extends BaseServiceImpl<SysOrgVehicleBean> implements SysOrgVehicleService {
    @Override
    @Transactional
    public void bind(SysOrgVehicleBean[] sysOrgVehicleBeans) {
        for(SysOrgVehicleBean sysOrgVehicleBean : sysOrgVehicleBeans){
            this.save(sysOrgVehicleBean);
        }
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] ids) {
        if(ids != null){
            for(Long id : ids){
                delete(id);
            }
        }
    }
}
