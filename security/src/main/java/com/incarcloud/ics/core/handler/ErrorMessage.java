package com.incarcloud.ics.core.handler;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/28
 */
public class ErrorMessage {

    public static final String UNKNOWN_CODE = "999";
    private boolean result = Boolean.FALSE;
    private String message = "";
    private String error = "";
    private String code = "";

    public ErrorMessage(String message) {
        this(message,null);
    }

    public ErrorMessage(String message,String code) {
        this(message, code, null);
    }

    public ErrorMessage(String message,String code, String error) {
        if(message!=null){
            this.message=message;
        }
        if(code!=null) {
            this.code = code;
        }
        if(error!=null) {
            this.error = error;
        }
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ErrorMessage of(String message,String code){
        return new ErrorMessage(message, code);
    }

    public static ErrorMessage unknownMessage(){
        return of("未知异常", UNKNOWN_CODE);
    }

    public String toJsonString() {
        return "{\"result\":"+this.isResult()+",\"message\":\""+this.getMessage()+"!\",\"error\":\""+this.getError()+"\",\"code\":\""+this.getCode()+"\"}";
    }


}
