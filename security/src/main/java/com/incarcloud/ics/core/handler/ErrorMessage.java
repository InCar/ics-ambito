package com.incarcloud.ics.core.handler;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/28
 */
public class ErrorMessage {
    private boolean result;
    private String message = "";
    private String error = "";
    private String code = "";

    public ErrorMessage() { }

    public ErrorMessage(boolean result) {
        this(result,null);
    }

    public ErrorMessage(boolean result, String message) {
        this(result, message,null);
    }

    public ErrorMessage(boolean result, String message,String code) {
        this(result, message,code,null);
    }

    public ErrorMessage(boolean result, String message,String code, String error) {
        this(result, message, code,error,null);
    }

    public ErrorMessage(boolean result, String message,String code, String error, T data) {
        this.result=result;
        if(message!=null){
            this.message=message;
        }
        if(code!=null) {
            this.code = code;
        }
        if(error!=null) {
            this.error = error;
        }
        this.data = data;
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
}
