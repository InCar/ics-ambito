package com.incarcloud.ics.pojo;

import com.incarcloud.ics.exception.AmbitoException;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/4
 */
public enum ErrorDefine {

    PARENT_ORG_NOT_EXISTS("10", "父组织不存在"),

    REPEATED_NAME("11", "名称重复"),

    SAVING_EXCEPTION("12", "保存异常"),

    REPEATED_CODE("13", "编号重复"),

    REPEATED_FIELD("14", "字段重复"),

    UNDELETABLE("15","无法删除"),

    REPEATED_USERNAME("16","用户名重复"),

    REPEATED_PHONE("17","手机号重复"),

    REPEATED_VIN_CODE("18","手机号重复"),

    UNMODIFIABLE_ORG_CODE("19","组织code不能修改"),

    INVALID_ORG_CODE("20","组织code格式错误"),

    ACCOUNT_NOT_EXISTS("51","账号不存在"),

    ACCOUNT_LOCKED("52","账号已锁定"),

    PASSWORD_NOT_MATCH("53","密码错误"),

    INVALID_SESSION("55","会话已失效"),

    UN_AUTHENTICATED("401","请先登录！"),

    UN_AUTHORIZATION("403","无访问权限"),

    UNKNOWN_EXCEPTION("999", "未知异常")
    ;
    private String code;
    private String message;

    ErrorDefine(String code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public AmbitoException toAmbitoException(){
        return AmbitoException.getException(this.getMessage(), this.code);
    }

    public JsonMessage toErrorMessage(){
        return JsonMessage.fail(this.getMessage(), this.getCode());
    }

    public JsonMessage toErrorMessage(String errorStack){
        return JsonMessage.fail(this.getMessage(), this.getCode(), errorStack);
    }

}
