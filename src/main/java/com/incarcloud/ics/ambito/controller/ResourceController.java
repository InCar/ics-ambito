package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.ResourceBean;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.ResourceService;
import com.incarcloud.ics.pojo.JsonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/24
 */
@RequestMapping(value = "/ics/resource")
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 查询资源信息
     * @param id id
     * @param page 当前页数
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping(value = "/list")
    public JsonMessage getList(@RequestParam(required = false)Long id,
                               @RequestParam(required = false)String resourceName,
                               @RequestParam(required = false)Integer page,
                               @RequestParam(required = false)Integer pageSize){
        Condition cond = Condition.and(
                new StringCondition("resourceName", resourceName, StringCondition.Handler.ALL_LIKE),
                new NumberCondition("id", id, NumberCondition.Handler.EQUAL)
        );
        if(page == null || pageSize == null){
            return JsonMessage.success(resourceService.query(cond));
        }else {
            return JsonMessage.success(resourceService.queryPage(new Page(page, pageSize), cond));
        }
    }


    /**
     * 保存资源
     * @param resourceBean
     * @return
     */
    @PostMapping(value = "/save")
    public JsonMessage save(@RequestBody ResourceBean resourceBean){
        if(resourceBean.getId() == null){
            resourceService.save(preHandle(resourceBean));
        }else {
            resourceService.update(resourceBean);
        }
        return JsonMessage.success();
    }


    private ResourceBean preHandle(ResourceBean resourceBean) {
        if(resourceBean.getParentId() == null || resourceBean.getParentId() == 0){
            resourceBean.setParentId(0L);
            resourceBean.setLevel((byte)0);
        }else {
            ResourceBean parent = resourceService.get(resourceBean.getParentId());
            resourceBean.setLevel((byte) (parent.getLevel()+1));
        }
        return resourceBean;
    }


    /**
     * 删除资源
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public JsonMessage deleteResources(@PathVariable long id){
        resourceService.delete(id);
        return JsonMessage.success();
    }



}
