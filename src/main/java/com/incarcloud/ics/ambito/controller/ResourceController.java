package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.entity.ResourceBean;
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
@RequestMapping(value = "/resource")
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 查询资源信息
     * @param id id
     * @param pageNum 当前页数
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping(value = "/list")
    public JsonMessage getList(@RequestParam(required = false)Long id,
                               @RequestParam(required = false)String resourceName,
                               @RequestParam(required = false)Integer pageNum,
                               @RequestParam(required = false)Integer pageSize){
        Object res = resourceService.getList(id, resourceName, pageNum, pageSize);
        return JsonMessage.success(res);
    }


    /**
     * 保存资源
     * @param resourceBean
     * @return
     */
    @PostMapping(value = "/save")
    public JsonMessage save(@RequestBody ResourceBean resourceBean){
        return JsonMessage.success(resourceService.saveOrUpdate(resourceBean));
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
