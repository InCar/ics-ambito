package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.entity.VehicleArchivesBean;
import com.incarcloud.ics.ambito.service.VehicleArchivesService;
import com.incarcloud.ics.pojo.JsonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by on 2018/12/26.
 */
@RequestMapping(value = "/vehicle")
public class VehicleArchivesController {

    @Autowired
    private VehicleArchivesService vehicleArchivesService;

    @GetMapping(value = "/list")
    public JsonMessage getVehicleList(@RequestParam(required = false) Long id,
                                      @RequestParam(required = false) String vinCode,
                                      @RequestParam(required = false) String plateNo,
                                      @RequestParam(required = false) Integer pageNum,
                                      @RequestParam(required = false) Integer pageSize) {
        Object res = vehicleArchivesService.getList(id,vinCode,plateNo,pageNum,pageSize);
        return JsonMessage.success(res);
    }

    @PostMapping(value = "/save")
    public JsonMessage saveArchive(@RequestBody VehicleArchivesBean vehicleArchivesBean) {
        VehicleArchivesBean newBean = vehicleArchivesService.saveOrUpdate(vehicleArchivesBean);
        return JsonMessage.success(newBean);
    }


    @DeleteMapping(value = "/delete/{id}")
    public JsonMessage deleteArchive(@PathVariable long id) {
        vehicleArchivesService.delete(id);
        return JsonMessage.success();
    }

}
