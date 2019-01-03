package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.SysOrgBean;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.SysOrgService;
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
@RequestMapping(value = "/sysorg")
@RestController
public class SysOrgController {

    @Autowired
    private SysOrgService sysOrgService;

    @GetMapping(value = "/list")
    public JsonMessage getList(@RequestParam(required = false)String orgName,
                               @RequestParam(required = false)String parentId,
                                   @RequestParam(required = false)Integer pageNum,
                                   @RequestParam(required = false)Integer pageSize){
        Condition cond = Condition.and(
                new StringCondition("orgName", orgName, StringCondition.Handler.ALL_LIKE),
                new StringCondition("parentId", parentId, StringCondition.Handler.EQUAL)
        );
        if (pageNum == null || pageSize == null) {
            return JsonMessage.success(sysOrgService.query(cond));
        } else {
            return JsonMessage.success(sysOrgService.queryPage(new Page(pageNum, pageSize), cond));
        }
    }

    @PostMapping(value = "/save")
    public JsonMessage save(@RequestBody SysOrgBean sysOrgBean){
        try {
            if(sysOrgBean.getId() == null){
                sysOrgService.save(sysOrgBean);
            }else {
                sysOrgService.update(sysOrgBean);
            }
            return JsonMessage.success();
        } catch (Exception e) {
            return JsonMessage.fail(e.getMessage());
        }
    }


    @DeleteMapping(value = "/delete/{id}")
    public JsonMessage delete(@PathVariable long id){
        sysOrgService.delete(id);
        return JsonMessage.success();
    }
}
