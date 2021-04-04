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

    public ResultObj(AbsCodeMsg absCodeMsg) {
        this(absCodeMsg.getcode(), absCodeMsg.getMsg());
    }

    public ResultObj(AbsCodeMsg absCodeMsg, T data) {
        this(absCodeMsg.getcode(), absCodeMsg.getMsg(), data);
    }

    public ResultObj<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public static <T> ResultObj<T> success(T data) {
        return new ResultObj(BaseCodeResult.SUCCESS, data);
    }

    public static <T> ResultObj<T> fail(AbsCodeMsg absCodeMsg) {
        return new ResultObj(absCodeMsg.getcode(), absCodeMsg.getMsg());
    }

    public static <T> ResultObj<T> fail() {
        return new ResultObj(BaseCodeResult.FAILE);
    }

}
