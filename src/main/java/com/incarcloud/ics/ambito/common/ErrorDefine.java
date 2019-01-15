package com.incarcloud.ics.ambito.common;

import com.incarcloud.ics.ambito.exception.AmbitoException;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.utils.ExceptionUtils;
import com.incarcloud.ics.core.exception.AccountNotExistsException;
import com.incarcloud.ics.core.exception.AuthException;
import com.incarcloud.ics.core.exception.CredentialNotMatchException;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/4
 */
public enum ErrorDefine {

    PARENT_ORG_NOT_EXISTS("父组织不存在", "10"),

    REPEATED_NAME("名称重复","11"),

    SAVING_EXCEPTION("保存异常","12"),

    REPEATED_CODE("编号重复","13"),

    UNDELETABLE("无法删除","15"),

    REPEATED_USERNAME("用户名重复","16"),

    REPEATED_PHONE("手机号重复","17"),

    AUTHENTICATE_FAILED("认证失败","50"),

    ACCOUNT_NOT_EXISTS("账号不存在","51"),

    ACCOUNT_LOCKED("账号已锁定","52"),

    PASSWORD_NOT_MATCH("密码错误","53"),

    UN_AUTHENTICATE("未认证","54"),

    UN_AUTHORIZATION("无访问权限","61"),

    UNKNOWN_EXCEPTION("未知异常", "999")
    ;
    private String code;
    private String message;

    ErrorDefine(String message, String code) {
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

    public static JsonMessage toErrorMessage(Exception e){
        String stackTraceAsString = ExceptionUtils.getStackTraceAsString(e);
        if(e instanceof AmbitoException){
            for(ErrorDefine err : ErrorDefine.values()){
                if(err.getCode().equals(((AmbitoException) e).getCode())){
                    return err.toErrorMessage();
                }
            }
        }
        if(e instanceof AuthException){
            if(e instanceof AccountNotExistsException){
                return ACCOUNT_NOT_EXISTS.toErrorMessage(stackTraceAsString);
            }
            if(e instanceof CredentialNotMatchException){
                return PASSWORD_NOT_MATCH.toErrorMessage();
            }
            return AUTHENTICATE_FAILED.toErrorMessage(stackTraceAsString);
        }

        return UNKNOWN_EXCEPTION.toErrorMessage(stackTraceAsString);
    }
}
