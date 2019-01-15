package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.UserBean;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.service.RoleService;
import com.incarcloud.ics.ambito.service.UserService;
import com.incarcloud.ics.core.authc.UsernamePasswordToken;
import com.incarcloud.ics.core.session.Session;
import com.incarcloud.ics.core.subject.Subject;
import com.incarcloud.ics.core.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/24
 */
@RequestMapping(value = "/ics/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    /**
     * 查询用户信息
     * @param id id
     * @param username 用户名
     * @param phone 手机号
     * @param realName 真实姓名
     * @param pageNum 当前页数
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping(value = "/list")
    public JsonMessage getUserList(@RequestParam(required = false)Long id,
                                   @RequestParam(required = false)String username,
                                   @RequestParam(required = false)String phone,
                                   @RequestParam(required = false)String realName,
                                   @RequestParam(required = false)String createUser,
                                   @RequestParam(required = false)Integer pageNum,
                                   @RequestParam(required = false)Integer pageSize){
        Condition cond = Condition.and(
                new NumberCondition("id", id, NumberCondition.Handler.EQUAL),
                new StringCondition("username", username, StringCondition.Handler.ALL_LIKE),
                new StringCondition("createUser", createUser, StringCondition.Handler.ALL_LIKE),
                new StringCondition("realName", realName, StringCondition.Handler.ALL_LIKE),
                new StringCondition("phone", phone, StringCondition.Handler.ALL_LIKE)
        );
        if(pageNum == null || pageSize == null){
            return JsonMessage.success(userService.query(cond));
        }else {
            return JsonMessage.success(userService.queryPage(new Page(pageNum, pageSize), cond));
        }
    }


    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping(value = "/rigister")
    public JsonMessage reigsterUser(@RequestBody UserBean user){
        return JsonMessage.success(userService.register(user));
    }



    /**
     * 获取用户角色
     * @param userId
     * @return
     */
    @GetMapping(value = "/roles")
    public JsonMessage getRoles(@RequestParam Long userId){
        return JsonMessage.success(roleService.getRolesOfUser(userId));
    }

    /**
     * 获取用户菜单
     * @param userId
     * @return
     */
    @GetMapping(value = "/menus")
    public JsonMessage getUserResources(@RequestParam Long userId){
        return JsonMessage.success(userService.getUserMenus(userId));
    }


    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PostMapping(value = "/update")
    public JsonMessage updateUser(@RequestBody UserBean user){
        userService.update(user);
        return JsonMessage.success();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public JsonMessage deleteUser(@PathVariable long id){
        userService.delete(id);
        return JsonMessage.success();
    }


    /**
     * 登录
     * @return
     */
    @PostMapping(value = "/login")
    public JsonMessage login(@RequestBody UsernamePasswordToken usernamePasswordToken){
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        Session session = subject.getSession();
        session.setAttribute("test", "123456");
        List<UserBean> userBeans = userService.query(new StringCondition("username", usernamePasswordToken.getPrincipal(), StringCondition.Handler.EQUAL));
        session.setAttribute("myInfo", userBeans.get(0));
        Assert.isTrue(subject.isAuthenticated(), "not login");
        Assert.isTrue(subject.getPrincipal().getUserIdentity() != null, "no identity after login");
        Assert.isTrue(subject.getSession(false)!= null, "has no session after login");
        return JsonMessage.success();
    }

    /**
     * 登出
     * @return
     */
    @PostMapping(value = "/logout")
    public JsonMessage logout(){
        Subject subject = SecurityUtils.getSubject();
        Assert.isTrue(subject.isAuthenticated(), "is not authenticated");
        subject.logout();
        Assert.isTrue(!subject.isAuthenticated(), "is authenticated");
        Assert.isTrue(subject.getSession(false) == null, "session is not null after logout");
        Assert.isTrue(subject.getPrincipal() == null, "principal is not null after logout");
        return JsonMessage.success();
    }

    /**
     * 获取用户信息
     * @param
     * @return
     */
    @GetMapping(value = "/myInfo")
    public JsonMessage myInfo(){
        Subject subject = SecurityUtils.getSubject();
        Assert.isTrue(subject.isAuthenticated(), "is not authenticated");
        Session session = subject.getSession();
        return JsonMessage.success(session.getAttribute("myInfo"));
    }
}
