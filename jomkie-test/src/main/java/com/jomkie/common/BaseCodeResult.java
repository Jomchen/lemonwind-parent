package com.jomkie.common;

/**
 * @author Jomkie
 * @date 2021/3/27 22:56:00
 * 请求结果信息码
 */
public enum BaseCodeResult implements AbsCodeMsg {

    FAILE(-1, "失败"),
    SUCCESS(0, "成功")
    ;

    Integer code;
    String msg;

    BaseCodeResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getcode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
