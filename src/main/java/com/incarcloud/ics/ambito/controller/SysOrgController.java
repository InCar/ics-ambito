package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.SysOrgBean;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.SysOrgService;
import com.incarcloud.ics.ambito.utils.CollectionUtils;
import com.incarcloud.ics.pojo.JsonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/26
 */
@RequestMapping(value = "/ics/sysorg")
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
        Condition cond = Condition.and(
                new NumberCondition("id", id, NumberCondition.Handler.EQUAL),
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
        if(sysOrgBean.getId() == null){
            sysOrgService.save(sysOrgBean);
        }else {
            sysOrgService.update(sysOrgBean);
        }
        return JsonMessage.success();
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
        SysOrgBean sysOrgBean = sysOrgService.get(id);
        if(sysOrgBean == null){
            return JsonMessage.success();
        }
        Map<String, SysOrgBean> sysOrgBeanMap = new HashMap<>();

        sysOrgBeanMap.put(sysOrgBean.getOrgCode(), sysOrgBean);
        List<SysOrgBean> sysOrgBeans = sysOrgService.query();
        if(CollectionUtils.isNotEmpty(sysOrgBeans)){
            sysOrgBeanMap.putAll(sysOrgBeans.stream().collect(Collectors.toMap(SysOrgBean::getOrgCode, e->e)));
            for(SysOrgBean org : sysOrgBeans){
                SysOrgBean parent = sysOrgBeanMap.get(org.getParentCode());
                if(parent != null){
                    parent.getChildren().add(org);
                }
            }
        }

        return JsonMessage.success(sysOrgBeanMap.get(sysOrgBean.getOrgCode()));
    }



}
