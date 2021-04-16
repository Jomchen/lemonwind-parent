package com.jomkie.common;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Jomkie
 * @date 2021/3/27 23:00:14
 * 请求结果封装
 */
@Getter
@Setter
public class ResultObj<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public ResultObj() {}

    public ResultObj(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultObj(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultObj(AbsResponse absResponse) {
        this(absResponse.getcode(), absResponse.getMsg());
    }

    public ResultObj(AbsResponse absResponse, T data) {
        this(absResponse.getcode(), absResponse.getMsg(), data);
    }

    public ResultObj<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public static <T> ResultObj<T> success() {
        return new ResultObj(BaseResponse.SUCCESS);
    }

    public static <T> ResultObj<T> success(T data) {
        return new ResultObj(BaseResponse.SUCCESS, data);
    }

    public static <T> ResultObj<T> fail(AbsResponse absResponse) {
        return new ResultObj(absResponse.getcode(), absResponse.getMsg());
    }

    public static <T> ResultObj<T> fail() {
        return new ResultObj(BaseResponse.FAILE);
    }

}
