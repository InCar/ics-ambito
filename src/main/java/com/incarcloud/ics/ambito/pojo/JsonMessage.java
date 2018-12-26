package com.incarcloud.ics.ambito.pojo;

import java.io.Serializable;

/**
 * Created by dave on 16/2/13.
 * @param <T>
 */
public class JsonMessage<T> implements Serializable{
    private boolean result;
    private String message = "";
    private String error = "";
    private String code="";
    private T data;

    public JsonMessage() { }

    public JsonMessage(boolean result) {
        this(result,null);
    }

    public JsonMessage(boolean result, String message) {
        this(result, message,null);
    }

    public JsonMessage(boolean result, String message,String code) {
        this(result, message,code,null);
    }

    public JsonMessage(boolean result, String message,String code, String error) {
        this(result, message, code,error,null);
    }

    public JsonMessage(boolean result, String message,String code, String error, T data) {
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

    public static <T> JsonMessage<T> success() {
        return new JsonMessage<>(true,null,String.valueOf(200));
    }

    public static <T> JsonMessage<T> success(T data) {
        return new JsonMessage<>(true,null,String.valueOf(200),null,data);
    }

    public static <T> JsonMessage<T> success(T data, String message) {
        return new JsonMessage<>(true,message,String.valueOf(200),null,data);
    }

    public static <T> JsonMessage<T> fail() {
        return new JsonMessage<>(false);
    }

    public static <T> JsonMessage<T> fail(String message) {
        return new JsonMessage<>(false, message);
    }

    public static <T> JsonMessage<T> fail(String message, String code) {
        return new JsonMessage<>(false, message,code);
    }

    public static <T> JsonMessage<T> fail(String message, String code, String error) {
        return new JsonMessage<>(false, message,code,error);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
