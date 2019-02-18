package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.RoleBean;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.ResourceService;
import com.incarcloud.ics.ambito.service.RoleResourceService;
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
                                   @RequestParam(required = false)Integer page,
                                   @RequestParam(required = false)Integer pageSize){
        Condition cond = Condition.and(
                new StringCondition("roleName", roleName, StringCondition.Handler.ALL_LIKE),
                new NumberCondition("id", id)
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
        if(roleBean.getId() == null){
//            uniqueCheck(roleBean);
            roleService.save(roleBean);
        }else {
//            RoleBean oldBean = roleService.get(roleBean.getId());
//            boolean roleCodeChange = !oldBean.getRoleCode().equals(roleBean.getRoleCode());
//            if(roleCodeChange){
//                roleCodeCheck(roleBean.getRoleCode());
//            }
//            boolean roleNameChange = !oldBean.getRoleName().equals(roleBean.getRoleName());
//            if(roleNameChange){
//                roleNameCheck(roleBean.getRoleName());
//            }
            roleService.update(roleBean);
        }
        return JsonMessage.success();
    }

//    private void uniqueCheck(RoleBean roleBean){
//        roleCodeCheck(roleBean.getRoleCode());
//        roleNameCheck(roleBean.getRoleName());
//    }
//
//    private void roleNameCheck(String roleName){
//        List<RoleBean> roleBeanList = roleService.query( new StringCondition("roleName", roleName));
//        if(!roleBeanList.isEmpty()){
//            throw ErrorDefine.REPEATED_NAME.toAmbitoException();
//        }
//    }
//
//    private void roleCodeCheck(String roleCode){
//        List<RoleBean> roleBeanList = roleService.query( new StringCondition("roleCode", roleCode));
//        if(!roleBeanList.isEmpty()){
//            throw ErrorDefine.REPEATED_CODE.toAmbitoException();
//        }
//    }

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
