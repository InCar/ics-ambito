package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.entity.VehicleArchivesBean;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.service.VehicleArchivesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by on 2018/12/26.
 */
@RequestMapping(value = "/user")
@RestController
public class VehicleArchivesController {

    @Autowired
    private VehicleArchivesService vehicleArchivesService;

    @GetMapping
    public JsonMessage getVehicleList(@RequestParam(value = "vinCode")String vinCode,
                                      @RequestParam(value = "plateNo") String plateNo,
                                      @RequestParam(required = false)Integer pageNum,
                                      @RequestParam(required = false)Integer pageSize){

        if(pageNum == null || pageSize == null){
            return JsonMessage.success(vehicleArchivesService.query("o.vin_code like concat('%',?,'%') and o.plate_no like concat('%',?,'%')", vinCode,plateNo));
        }else {
            return JsonMessage.success(vehicleArchivesService.queryPage(new Page(pageNum, pageSize),"o.vin_code like concat('%',?,'%') and o.plate_no like concat('%',?,'%')", vinCode,plateNo));
        }
    }

    @PostMapping(value = "/save")
    public JsonMessage saveUser(@RequestBody VehicleArchivesBean vehicleArchivesBean){
        try {
            if(vehicleArchivesBean.getId() == null){
                vehicleArchivesService.save(vehicleArchivesBean);
            }else {
                vehicleArchivesService.update(vehicleArchivesBean);
            }
            return JsonMessage.success();
        } catch (Exception e) {
            return JsonMessage.fail(e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public JsonMessage deleteUser(@PathVariable long id){
        vehicleArchivesService.delete(id);
        return JsonMessage.success();
    }

}
