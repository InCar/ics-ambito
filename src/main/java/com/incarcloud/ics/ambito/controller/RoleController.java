package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.entity.*;
import com.incarcloud.ics.ambito.service.*;
import com.incarcloud.ics.pojo.JsonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/24
 */
@RequestMapping(value = "/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private ResourceService resourceService;
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
                                   @RequestParam(required = false)Integer pageNum,
                                   @RequestParam(required = false)Integer pageSize){
        Object res = roleService.getList(id, roleName, pageNum, pageSize);
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


    /**
     * 获取角色所有用户
     * @param roleId 角色id
     * @return
     */
    @GetMapping(value = "/users")
    public JsonMessage getAllUsersOfRole(@RequestParam long roleId){
        List<UserRoleBean> userRoleBeanList = userRoleService.query(new NumberCondition("roleId", roleId));
        Set<Long> userIds = userRoleBeanList.stream().map(UserRoleBean::getUserId).collect(Collectors.toSet());
        List<UserBean> userBeans = userService.query(new NumberCondition("id",userIds, NumberCondition.Handler.IN));
        return JsonMessage.success(userBeans);
    }


    /**
     * 获取角色所有资源
     * @param roleId 角色id
     * @return
     */
    @GetMapping(value = "/resources")
    public JsonMessage getAllResourcesOfRole(@RequestParam long roleId){
        List<RoleResourceBean> roleResourceBeans = roleResourceService.query(new NumberCondition("roleId", roleId));
        Set<Long> resIds = roleResourceBeans.stream().map(RoleResourceBean::getResourceId).collect(Collectors.toSet());
        List<ResourceBean> resourceBeans = resourceService.query(Condition.and(
                new NumberCondition("id", resIds, NumberCondition.Handler.IN)
        ));
        return JsonMessage.success(resourceBeans);
    }



}
