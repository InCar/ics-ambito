package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.RoleBean;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/24
 */
@RequestMapping(value = "/api/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询用户信息
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
        Condition cond = Condition.and(
                new StringCondition("rolename", roleName, StringCondition.Handler.ALL_LIKE),
                new NumberCondition("id", id, NumberCondition.Handler.EQUAL)
        );
        if(page == null || pageSize == null){
            return JsonMessage.success(roleService.query(cond));
        }else {
            return JsonMessage.success(roleService.queryPage(new Page(page, pageSize), cond));
        }
    }


    /**
     * 保存角色
     * @param roleBean
     * @return
     */
    @PostMapping(value = "/save")
    public JsonMessage save(@RequestBody RoleBean roleBean){
        try {
            if(roleBean.getId() == null){
                roleService.save(roleBean);
            }else {
                roleService.update(roleBean);
            }
            return JsonMessage.success();
        } catch (Exception e) {
            return JsonMessage.fail(e.getMessage());
        }
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
