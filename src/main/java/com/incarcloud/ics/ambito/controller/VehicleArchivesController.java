package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.VehicleArchivesBean;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.VehicleArchivesService;
import com.incarcloud.ics.core.security.SecurityUtils;
import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.pojo.JsonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by on 2018/12/26.
 */
@RequestMapping(value = "/ics/vehicle")
@RestController
public class VehicleArchivesController {

    @Autowired
    private VehicleArchivesService vehicleArchivesService;

    @GetMapping(value = "/list")
    public JsonMessage getVehicleList(@RequestParam(required = false) Long id,
                                      @RequestParam(required = false) String vinCode,
                                      @RequestParam(required = false) String plateNo,
                                      @RequestParam(required = false) Integer pageNum,
                                      @RequestParam(required = false) Integer pageSize) {
        Subject subject = SecurityUtils.getSubject();
        Collection<String> accessibleOrgs = subject.getFilterCodes(VehicleArchivesBean.class);

        Condition condition = Condition.and(
                new NumberCondition("id", id, NumberCondition.Handler.EQUAL),
                new StringCondition("vin_code", vinCode, StringCondition.Handler.ALL_LIKE),
                new StringCondition("plate_no", plateNo, StringCondition.Handler.ALL_LIKE),
                new StringCondition("org_code", accessibleOrgs, StringCondition.Handler.IN)
        );


        if (pageNum == null || pageSize == null) {
            return JsonMessage.success(vehicleArchivesService.query(condition));
        } else {
            return JsonMessage.success(vehicleArchivesService.queryPage(new Page(pageNum, pageSize), condition));
        }
    }

    @PostMapping(value = "/save")
    public JsonMessage saveArchive(@RequestBody VehicleArchivesBean vehicleArchivesBean) {
        if (vehicleArchivesBean.getId() == null) {
            vehicleArchivesService.save(vehicleArchivesBean);
        } else {
            vehicleArchivesService.update(vehicleArchivesBean);
        }
        return JsonMessage.success();
    }

    @DeleteMapping(value = "/delete/{id}")
    public JsonMessage deleteArchive(@PathVariable long id) {
        vehicleArchivesService.delete(id);
        return JsonMessage.success();
    }

}
