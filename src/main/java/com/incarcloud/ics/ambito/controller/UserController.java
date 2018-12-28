package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NullCondition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.UserBean;
import com.incarcloud.ics.ambito.exception.AmbitoException;
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
@RequestMapping(value = "/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户信息
     * @param id id
     * @param username 用户名
     * @param phone 手机号
     * @param realName 真实姓名
     * @param page 当前页数
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping(value = "/list")
    public JsonMessage getUserList(@RequestParam(required = false)Long id,
                                   @RequestParam(required = false)String username,
                                   @RequestParam(required = false)String phone,
                                   @RequestParam(required = false)String realName,
                                   @RequestParam(required = false)String createUser,
                                   @RequestParam(required = false)Integer page,
                                   @RequestParam(required = false)Integer pageSize){
        Condition cond = Condition.and(
                new NumberCondition("id", id, NumberCondition.Handler.EQUAL),
                new StringCondition("username", username, StringCondition.Handler.ALL_LIKE),
                new StringCondition("createUser", createUser, StringCondition.Handler.ALL_LIKE),
                new StringCondition("realName", realName, StringCondition.Handler.ALL_LIKE),
                new StringCondition("phone", phone, StringCondition.Handler.ALL_LIKE)
        );
        if(page == null || pageSize == null){
            return JsonMessage.success(userService.query(cond));
        }else {
            return JsonMessage.success(userService.queryPage(new Page(page, pageSize), cond));
        }
    }


    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping(value = "/rigister")
    public JsonMessage reigsterUser(@RequestBody UserBean user){
        try {
            return JsonMessage.success(userService.register(user));
        } catch (AmbitoException e) {
            return JsonMessage.fail(e.getMessage(), e.getCode());
        }
    }


    @GetMapping(value = "/userDetail/{id}")
    public JsonMessage getDetail(@PathVariable long id){
        try {
            UserBean userBean = userService.get(id);
            return JsonMessage.success(userBean);
        } catch (AmbitoException e) {
            return JsonMessage.fail(e.getMessage());
        }
    }


    @PostMapping(value = "/update")
    public JsonMessage updateUser(@RequestBody UserBean user){
        try {
            userService.update(user);
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
