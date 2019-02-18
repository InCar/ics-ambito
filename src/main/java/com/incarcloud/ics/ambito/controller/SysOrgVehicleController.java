package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.SysOrgVehicleBean;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.SysOrgVehicleService;
import com.incarcloud.ics.pojo.JsonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        if (sysOrgVehicleBean.getId() == null) {
            sysOrgVehicleService.save(sysOrgVehicleBean);
        } else {
            sysOrgVehicleService.update(sysOrgVehicleBean);
        }
        return JsonMessage.success();
    }

    /**
     * 绑定车辆
     * @param sysOrgVehicleBeans
     * @return
     */
    @PostMapping(value = "/bind")
    public JsonMessage bind(@RequestBody SysOrgVehicleBean[] sysOrgVehicleBeans) {
        sysOrgVehicleService.bind(sysOrgVehicleBeans);
        return JsonMessage.success();
    }


    /**
     * 删除车辆绑定关系
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public JsonMessage delete(@PathVariable long id) {
        sysOrgVehicleService.delete(id);
        return JsonMessage.success();
    }

    /**
     * 解绑车辆
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/unbind")
    public JsonMessage delete(@RequestParam Long[] ids) {
        sysOrgVehicleService.deleteBatch(ids);
        return JsonMessage.success();
    }
}
