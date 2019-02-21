package com.incarcloud.ics.ambito.service.impl;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.VehicleArchivesBean;
import com.incarcloud.ics.ambito.jdbc.BaseServiceImpl;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.VehicleArchivesService;
import com.incarcloud.ics.core.security.SecurityUtils;
import com.incarcloud.ics.core.subject.Subject;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by on 2018/12/26.
 */
@Service
public class VehicleArchivesServicesImpl extends BaseServiceImpl<VehicleArchivesBean> implements VehicleArchivesService{
    @Override
    public VehicleArchivesBean saveOrUpdate(VehicleArchivesBean vehicleArchivesBean) {
        if (vehicleArchivesBean.getId() == null) {
            this.save(vehicleArchivesBean);
        } else {
            this.update(vehicleArchivesBean);
        }
        List<VehicleArchivesBean> vehicleArchivesBeanList = this.query(new StringCondition("vinCode",vehicleArchivesBean.getVinCode() ));
        return vehicleArchivesBeanList.get(0);
    }

    @Override
    public Object getList(Long id, String vinCode, String plateNo, Integer pageNum, Integer pageSize) {
        Subject subject = SecurityUtils.getSubject();
        Collection<String> accessibleOrgs = subject.getFilterCodes(VehicleArchivesBean.class);
        Condition condition = Condition.and(
                new NumberCondition("id", id, NumberCondition.Handler.EQUAL),
                new StringCondition("vin_code", vinCode, StringCondition.Handler.ALL_LIKE),
                new StringCondition("plate_no", plateNo, StringCondition.Handler.ALL_LIKE),
                new StringCondition("org_code", accessibleOrgs, StringCondition.Handler.IN)
        );
        if (pageNum == null || pageSize == null) {
            return this.query(condition);
        } else {
            return this.queryPage(new Page(pageNum, pageSize), condition);
        }
    }
}
