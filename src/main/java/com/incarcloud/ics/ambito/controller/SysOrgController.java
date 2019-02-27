package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.entity.SysOrgBean;
import com.incarcloud.ics.ambito.service.SysOrgService;
import com.incarcloud.ics.pojo.JsonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/26
 */
@RequestMapping(value = "/sysorg")
@RestController
public class SysOrgController {

    @Autowired
    private SysOrgService sysOrgService;

    @GetMapping(value = "/list")
    public JsonMessage getList(@RequestParam(required = false)Long id,
                               @RequestParam(required = false)String orgName,
                               @RequestParam(required = false)String parentId,
                               @RequestParam(required = false)Integer pageNum,
                               @RequestParam(required = false)Integer pageSize){
        Object res = sysOrgService.getList(id, orgName, parentId, pageNum, pageSize);
        return JsonMessage.success(res);
    }

    @PostMapping(value = "/save")
    public JsonMessage save(@RequestBody SysOrgBean sysOrgBean){
        SysOrgBean orgBean = sysOrgService.saveOrUpdate(sysOrgBean);
        return JsonMessage.success(orgBean);
    }



    @DeleteMapping(value = "/delete/{id}")
    @Transactional
    public JsonMessage delete(@PathVariable long id){
        sysOrgService.delete(id);
        return JsonMessage.success();
    }


    /**
     * 查询组织及其所属组织
     * @param id 组织id
     * @return
     */
    @GetMapping(value = "/orgTree")
    public JsonMessage getOrgTree(@RequestParam Long id){
        SysOrgBean orgTree = sysOrgService.getOrgTree(id);
        return JsonMessage.success(orgTree);
    }



}
