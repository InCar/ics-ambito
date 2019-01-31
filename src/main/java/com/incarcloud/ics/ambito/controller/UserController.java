package com.incarcloud.ics.ambito.controller;

import com.incarcloud.ics.ambito.common.ErrorDefine;
import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.condition.impl.NumberCondition;
import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.entity.SysOrgBean;
import com.incarcloud.ics.ambito.entity.UserBean;
import com.incarcloud.ics.ambito.entity.VehicleArchivesBean;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.pojo.Page;
import com.incarcloud.ics.ambito.pojo.PageResult;
import com.incarcloud.ics.ambito.service.RoleService;
import com.incarcloud.ics.ambito.service.UserService;
import com.incarcloud.ics.core.aspect.anno.Logic;
import com.incarcloud.ics.core.aspect.anno.RequiresRoles;
import com.incarcloud.ics.core.authc.UsernamePasswordToken;
import com.incarcloud.ics.core.privilege.WildcardPrivilege;
import com.incarcloud.ics.core.security.SecurityUtils;
import com.incarcloud.ics.core.session.Session;
import com.incarcloud.ics.core.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Collection;
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
            List<UserBean> query = userService.query(cond);
            maskCredential(query);
            return JsonMessage.success(query);
        }else {
            PageResult<UserBean> query = userService.queryPage(new Page(pageNum, pageSize), cond);
            maskCredential(query.getContent());
            return JsonMessage.success(query);
        }
    }


    private void maskCredential(Collection<UserBean> collection){
        collection.forEach(e->{
            e.setSalt(null);
            e.setPassword(null);
        });
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
        List<UserBean> userBeans = userService.query(new StringCondition("username", usernamePasswordToken.getPrincipal(), StringCondition.Handler.EQUAL));
        session.setAttribute("myInfo", userBeans.get(0));
        return JsonMessage.success();
    }

    /**
     * 登出
     * @return
     */
    @PostMapping(value = "/logout")
    public JsonMessage logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
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
        if(!subject.isAuthenticated()){
            return ErrorDefine.UN_AUTHENTICATED.toErrorMessage();
        }
        Session session = subject.getSession();
        return JsonMessage.success(session.getAttribute("myInfo"));
    }


    @GetMapping(value = "/test")
    @RequiresRoles(value = {"admin1","admin"}, logic = Logic.AND)
    public JsonMessage test(){
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            return ErrorDefine.UN_AUTHENTICATED.toErrorMessage();
        }
        Assert.isTrue(subject.hasRole("admin"), "");
        Assert.isTrue(!subject.hasRole("user"), "");
        Assert.isTrue(subject.isPermitted(new WildcardPrivilege("abc")), "");
        Assert.isTrue(subject.isPermitted(new WildcardPrivilege("bcd")), "");
        Assert.isTrue(subject.isPermittedAllObjectPrvileges(Arrays.asList(new WildcardPrivilege("bcd"), new WildcardPrivilege("abc"))), "");
        Assert.isTrue(!subject.isAccessibleForData(1L, VehicleArchivesBean.class),"");
        Assert.isTrue(subject.isAccessibleForData(2L, VehicleArchivesBean.class),"");
        Assert.isTrue(subject.isAccessibleForData(7L, SysOrgBean.class),"");
        return JsonMessage.success();
    }



    @GetMapping(value = "/getOrgs")
    public JsonMessage getOrgs(){
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            return ErrorDefine.UN_AUTHENTICATED.toErrorMessage();
        }
        Collection<String> accessibleOrg = subject.getFilterCodes(VehicleArchivesBean.class);
        return JsonMessage.success(accessibleOrg);
    }

}
