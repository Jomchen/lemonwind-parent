package com.jomkie.common;

/**
 * @author Jomkie
 * @since 2021-04-26 11:33:55
 * 异常类
 */
public class LemonException  extends RuntimeException {

    private Integer code;
    private String message;

    public LemonException() {
        super();
    }

    public LemonException(String message) {
        super(message);
        this.message = message;
    }

    public LemonException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    public LemonException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public LemonException(Integer code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
    }

}
