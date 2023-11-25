package com.lemonwind.test.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lemonwind
 * @since 2021-04-26 11:33:55
 * 异常类
 */
@Getter
@Setter
public class LemonException  extends RuntimeException {

    private Integer code;
    private String message;

    public LemonException() {
        super();
    }

    public LemonException(AbsResponse baseResponse) {
        super(baseResponse.getMsg());
        this.code = baseResponse.getcode();
        this.message = baseResponse.getMsg();
    }

    public LemonException(AbsResponse baseResponse, Throwable throwable) {
        super(baseResponse.getMsg(), throwable);
        this.code = baseResponse.getcode();
        this.message = baseResponse.getMsg();
    }

    public LemonException(AbsResponse baseResponse, String message, Throwable throwable) {
        super(baseResponse.getMsg(), throwable);
        this.code = baseResponse.getcode();
        this.message = message;
    }

    public LemonException(AbsResponse baseResponse, String message) {
        super();
        this.code = baseResponse.getcode();
        this.message = message;
    }

    public LemonException(String message) {
        super(message);
        this.code = Responsecode.SYSTEM_ERROR.getcode();
        this.message = message;
    }

    public LemonException(String message, Throwable throwable) {
        super(message, throwable);
        this.code = Responsecode.SYSTEM_ERROR.getcode();
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
