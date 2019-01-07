package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.VehicleArchivesBean;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.VehicleArchivesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by on 2018/12/26.
 */
@RequestMapping(value = "/ics/vehicle")
@RestController
public class VehicleArchivesController {

    @Autowired
    private VehicleArchivesService vehicleArchivesService;

    @GetMapping(value = "/list")
    public JsonMessage getVehicleList(@RequestParam(value = "vinCode") String vinCode,
                                      @RequestParam(value = "plateNo") String plateNo,
                                      @RequestParam(required = false) Integer pageNum,
                                      @RequestParam(required = false) Integer pageSize) {

        Condition condition = Condition.and(new StringCondition("vin_code", vinCode, StringCondition.Handler.ALL_LIKE),
                new StringCondition("plate_no", plateNo, StringCondition.Handler.ALL_LIKE));

        if (pageNum == null || pageSize == null) {
            return JsonMessage.success(vehicleArchivesService.query(condition));
        } else {
            return JsonMessage.success(vehicleArchivesService.queryPage(new Page(pageNum, pageSize), condition));
        }
    }

    @PostMapping(value = "/save")
    public JsonMessage saveUser(@RequestBody VehicleArchivesBean vehicleArchivesBean) {
        if (vehicleArchivesBean.getId() == null) {
            vehicleArchivesService.save(vehicleArchivesBean);
        } else {
            vehicleArchivesService.update(vehicleArchivesBean);
        }
        return JsonMessage.success();
    }

    @DeleteMapping(value = "/delete/{id}")
    public JsonMessage deleteUser(@PathVariable long id) {
        vehicleArchivesService.delete(id);
        return JsonMessage.success();
    }

}
