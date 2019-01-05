package com.incarcloud.ics.ambito.common;

import com.incarcloud.ics.ambito.exception.AmbitoException;

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

    UNKNOWN_EXCEPTION("未知异常", "99"),

    ;
    private String code;
    private String message;

    private ErrorDefine(String code, String message) {
        this.code = code;
        this.message = message;
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

    public AmbitoException toErrorMessage(){
        return AmbitoException.getException(this.getMessage(), this.code);
    }
}
