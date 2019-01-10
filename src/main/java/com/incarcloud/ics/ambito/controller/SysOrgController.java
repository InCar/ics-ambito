package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.common.ErrorDefine;
import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NullCondition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.SysOrgBean;
import com.incarcloud.ics.ambito.exception.AmbitoException;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.SysOrgService;
import com.incarcloud.ics.ambito.service.SysOrgUserService;
import com.incarcloud.ics.ambito.service.SysOrgVehicleService;
import com.incarcloud.ics.ambito.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static com.incarcloud.ics.ambito.common.ErrorDefine.PARENT_ORG_NOT_EXISTS;
import static com.incarcloud.ics.ambito.common.ErrorDefine.UNKNOWN_EXCEPTION;


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
    public JsonMessage getList( @RequestParam(required = false)Long id,
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
