package com.incarcloud.ics.exception;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/27
 */
public class AmbitoException extends RuntimeException{

    private static final long serialVersionUID = 6012050882440753873L;

    private String code;

    public AmbitoException() {
        super();
    }

    public AmbitoException(String message) {
        super(message);
    }

    public AmbitoException(String message, String code) {
        super(message);
        this.code = code;
    }

    public AmbitoException(String message, Throwable cause) {
        super(message, cause);
    }

    public AmbitoException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static AmbitoException getException(String message){
        return new AmbitoException(message);
    }

    public static AmbitoException getException(String message, Throwable cause){
        return new AmbitoException(message, cause);
    }

    public static AmbitoException getException(String message, String code){
        return new AmbitoException(message, code);
    }

}
