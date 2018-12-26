package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.entity.UserBean;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/24
 */
@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/list")
    public JsonMessage getUserList(@RequestParam(required = false)String username,
                                   @RequestParam(required = false)Integer page,
                                   @RequestParam(required = false)Integer pageSize){
        if(page == null || pageSize == null){
            return JsonMessage.success(userService.query("o.username = ?", username));
        }else {
            return JsonMessage.success(userService.queryPage(new Page(page, pageSize),"o.username like concat('%',?,'%')",  username));
        }
    }


    @PostMapping(value = "/save")
    public JsonMessage saveUser(@RequestBody UserBean user){
        try {
            if(user.getId() == null){
                userService.save(user);
            }else {
                userService.update(user);
            }
            return JsonMessage.success();
        } catch (Exception e) {
            return JsonMessage.fail(e.getMessage());
        }
    }


    @DeleteMapping(value = "/delete/{id}")
    public JsonMessage deleteUser(@PathVariable long id){
        userService.delete(id);
        return JsonMessage.success();
    }
}
