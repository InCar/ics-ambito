package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.SysOrgUserBean;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.SysOrgUserService;
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
@RequestMapping(value = "/ics/orgUser")
@RestController
public class SysOrgUserController {

    @Autowired
    private SysOrgUserService sysOrgUserService;

    @GetMapping(value = "/list")
    public JsonMessage getList(@RequestParam(required = false) Long userId,
                               @RequestParam(required = false) Long orgId,
                               @RequestParam(required = false) Integer pageNum,
                               @RequestParam(required = false) Integer pageSize) {

        Condition cond = Condition.and(
                new StringCondition("orgId", orgId, StringCondition.Handler.EQUAL),
                new StringCondition("userId", userId, StringCondition.Handler.EQUAL)
        );
        if (pageNum == null || pageSize == null) {
            return JsonMessage.success(sysOrgUserService.query(cond));
        } else {
            return JsonMessage.success(sysOrgUserService.queryPage(new Page(pageNum, pageSize), cond));
        }
    }


    @PostMapping(value = "/save")
    public JsonMessage save(@RequestBody SysOrgUserBean sysOrgUserBean) {
        try {
            if (sysOrgUserBean.getId() == null) {
                sysOrgUserService.save(sysOrgUserBean);
            } else {
                sysOrgUserService.update(sysOrgUserBean);
            }
            return JsonMessage.success();
        } catch (Exception e) {
            return JsonMessage.fail(e.getMessage());
        }
    }


    @DeleteMapping(value = "/delete/{id}")
    public JsonMessage delete(@PathVariable long id) {
        sysOrgUserService.delete(id);
        return JsonMessage.success();
    }
}
