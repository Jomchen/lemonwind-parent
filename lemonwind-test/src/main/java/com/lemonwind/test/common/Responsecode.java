package com.lemonwind.test.common;

/**
 * @author Jomkie
 * @since 2021/3/27 22:56:00
 * 请求结果信息码
 */
public enum Responsecode implements AbsResponse {

    FAILE(-1, "请求失败"),
    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数不合法"),
    REMOTE_ERROR(2, "远程请求异常"),
    REMOTE_FAIL(2, "远程请求失败"),
    REMOTE_NO_RESPONSE(3, "远程请求无响应"),
    GENERATED_QDIMAGE_FAIL(4, "生成二维码失败"),
    ACQUIRE_TARGET_FAIL(5, "未获取相应的关键信息"),
    SYSTEM_ERROR(500, "系统异常"),

    ;

    private Integer code;
    private String msg;

    Responsecode(Integer code, String msg) {
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
