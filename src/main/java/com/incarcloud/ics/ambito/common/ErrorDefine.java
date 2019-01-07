package com.incarcloud.ics.ambito.common;

import com.incarcloud.ics.ambito.exception.AmbitoException;
import com.incarcloud.ics.ambito.pojo.JsonMessage;
import com.incarcloud.ics.ambito.utils.logger.ExceptionUtils;

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

    UNKNOWN_EXCEPTION("未知异常", "999")
    ;
    private String code;
    private String message;

    private ErrorDefine(String message, String code) {
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
        if(e instanceof AmbitoException){
            for(ErrorDefine err : ErrorDefine.values()){
                if(err.getCode().equals(((AmbitoException) e).getCode())){
                    return err.toErrorMessage();
                }
            }
        }
        return UNKNOWN_EXCEPTION.toErrorMessage(ExceptionUtils.getStackTraceAsString(e));
    }
}
