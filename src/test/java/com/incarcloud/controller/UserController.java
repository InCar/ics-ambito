//package com.incarcloud.controller;
//
//import UserBean;
//import JsonMessage;
//import UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author ThomasChan
// * @version 1.0
// * @description
// * @date 2018/12/24
// */
//@RequestMapping(value = "/user")
//@RestController
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @RequestMapping(value = "/list")
//    public JsonMessage getUserList(){
//        return JsonMessage.success(userService.query());
//    }
//
//    @PostMapping(value = "/save")
//    public JsonMessage saveUser(@RequestBody UserBean user){
//        return null;
//    }
//
//
//    @DeleteMapping(value = "/delete")
//    public JsonMessage saveUser(@PathVariable long[] ids){
//        return null;
//    }
//}
