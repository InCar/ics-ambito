package com.incarcloud.ics.core.exception;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/28
 */
public class BeanInitializationException extends RuntimeException {
    private static final long serialVersionUID = -2607700763030008097L;

    public BeanInitializationException() {
        super();
    }

    public BeanInitializationException(String message) {
        super(message);
    }

    public BeanInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanInitializationException(Throwable cause) {
        super(cause);
    }
}
