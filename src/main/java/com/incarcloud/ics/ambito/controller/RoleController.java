package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.entity.RoleBean;
import com.incarcloud.ics.ambito.service.RoleService;
import com.incarcloud.ics.pojo.JsonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/24
 */
@RequestMapping(value = "/ics/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询角色列表信息
     * @param id id
     * @param page 当前页数
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping(value = "/list")
    public JsonMessage getList(@RequestParam(required = false)Long id,
                                   @RequestParam(required = false)String roleName,
                                   @RequestParam(required = false)Integer page,
                                   @RequestParam(required = false)Integer pageSize){
        Object res = roleService.getList(id, roleName, page, pageSize);
        return JsonMessage.success(res);
    }


    /**
     * 保存角色
     * @param roleBean
     * @return
     */
    @PostMapping(value = "/save")
    public JsonMessage save(@RequestBody RoleBean roleBean){
        RoleBean newRole = roleService.saveOrUpdate(roleBean);
        return JsonMessage.success(newRole);
    }


    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public JsonMessage delete(@PathVariable long id){
        roleService.delete(id);
        return JsonMessage.success();
    }


}
