package com.jomkie.common;

/**
 * @author Jomkie
 * @date 2021/3/27 22:56:00
 * 请求结果信息码
 */
public enum BaseResponse implements AbsResponse {

    FAILE(-1, "请求失败"),
    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数不合法")
    ;

    Integer code;
    String msg;

    BaseResponse(Integer code, String msg) {
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
