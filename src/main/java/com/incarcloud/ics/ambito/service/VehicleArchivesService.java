package com.incarcloud.ics.ambito.service;

import com.incarcloud.ics.ambito.entity.VehicleArchivesBean;
import com.incarcloud.ics.ambito.jdbc.BaseService;

/**
 * Created by LiXiaolan on 2018/12/26.
 */
public interface VehicleArchivesService extends BaseService<VehicleArchivesBean> {
    VehicleArchivesBean saveOrUpdate(VehicleArchivesBean vehicleArchivesBean);

    Object getList(Long id, String vinCode, String plateNo, Integer pageNum, Integer pageSize);
}
