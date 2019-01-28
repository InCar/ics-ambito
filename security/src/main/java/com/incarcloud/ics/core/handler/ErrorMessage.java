package com.incarcloud.ics.core.handler;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/28
 */
public class ErrorMessage {
    private boolean result;
    private String message;
    private String error = "";
    private String code = "";

    public ErrorMessage(boolean result, String message, String error, String code) {
        this.result = result;
        this.message = message;
        this.error = error;
        this.code = code;
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
