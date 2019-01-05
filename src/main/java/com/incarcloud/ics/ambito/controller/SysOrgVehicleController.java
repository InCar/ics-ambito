package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.SysOrgVehicleBean;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.SysOrgVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/26
 */
@RequestMapping(value = "/ics/orgvehicle")
@RestController
public class SysOrgVehicleController {

    @Autowired
    private SysOrgVehicleService sysOrgVehicleService;

    @GetMapping(value = "/list")
    public JsonMessage getList(@RequestParam(required = false) Long vehicleId,
                               @RequestParam(required = false) Long orgId,
                               @RequestParam(required = false) Integer pageNum,
                               @RequestParam(required = false) Integer pageSize) {

        Condition cond = Condition.and(
                new StringCondition("orgId", orgId, StringCondition.Handler.EQUAL),
                new StringCondition("vehicleId", vehicleId, StringCondition.Handler.EQUAL)
        );
        if (pageNum == null || pageSize == null) {
            return JsonMessage.success(sysOrgVehicleService.query(cond));
        } else {
            return JsonMessage.success(sysOrgVehicleService.queryPage(new Page(pageNum, pageSize), cond));
        }
    }


    @PostMapping(value = "/save")
    public JsonMessage save(@RequestBody SysOrgVehicleBean sysOrgVehicleBean) {
        try {
            if (sysOrgVehicleBean.getId() == null) {
                sysOrgVehicleService.save(sysOrgVehicleBean);
            } else {
                sysOrgVehicleService.update(sysOrgVehicleBean);
            }
            return JsonMessage.success();
        } catch (Exception e) {
            return JsonMessage.fail(e.getMessage());
        }
    }


    @DeleteMapping(value = "/delete/{id}")
    public JsonMessage delete(@PathVariable long id) {
        sysOrgVehicleService.delete(id);
        return JsonMessage.success();
    }
}
