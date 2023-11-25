package com.lemonwind.test.common.wechat;

import lombok.Getter;

/**
 * @author Jomkie
 * @since 2021-05-20 12:59:47
 * 微信扫码支付结果码
 */
@Getter
public enum WeChatStatusCode {

    /** 世界只需要那瘦小可爱的企鹅，不需要那红脖油胖的异种；愿多一次微信支付，胖鹅与乔布斯的茶会更近一天。*/

    /* 正题：
             特别说明：微信支付接口错误码 和 微信公共错误码 有很多相同的错误码，这里已经作出了分离
             SIGN_ERROR 和 INVALID_REQUEST 在微信支付接口错误码 和 微信公共错误码 中有共同的 错误码 和 错误信息，
             但是中文说明有丁点不同，这里屏蔽 微信支付接口错误码 中重复的部分
             ------------------------------------------------------- */

    /*----- 微信支付接口错误码（已剔除和公共错误码相同的部分） ----- */
    /*SIGN_ERROR(401, "SIGN_ERROR", "签名错误", "请检查签名参数和方法是否都符合签名算法要求"),*/
    ORDERNOTEXIST(404, "ORDERNOTEXIST", "订单不存在", "请检查订单是否发起过交易"),
    OPENID_MISMATCH(500, "OPENID_MISMATCH", "openid和appid不匹配", "请确认openid和appid是否匹配"),
    NOAUTH(403, "NOAUTH", "商户无权限", "请商户前往申请此接口相关权限"),
    INVALID_TRANSACTIONID(500, "INVALID_TRANSACTIONID", "订单号非法", "请检查微信支付订单号是否正确"),
    /*INVALID_REQUEST(400, "INVALID_REQUEST", "无效请求", "请根据接口返回的详细信息检查"),*/


    /*----- 微信公共错误码 ----- */
    APPID_MCHID_NOT_MATCH(400, "APPID_MCHID_NOT_MATCH", "商户号与appid不匹配", "请绑定调用接口的商户号和APPID后重试"),
    BANK_ERROR(500, "BANK_ERROR", "银行系统异常", "银行系统异常，请用相同参数重新调用"),
    OUT_TRADE_NO_USED(403, "OUT_TRADE_NO_USED", "商户订单号重复", "请核实商户订单号是否重复提交"),
    REQUEST_BLOCKED(403, "REQUEST_BLOCKED", "请求受阻", "此状态代表退款申请失败，商户可根据具体的错误提示做相应的处理"),
    BIZ_ERR_NEED_RETRY(500, "BIZ_ERR_NEED_RETRY", "退款业务流程错误", "请不要更换商户退款单号，请使用相同参数再次调用API"),
    USERPAYING(202, "USERPAYING", "用户支付中，需要输入密码", "等待5秒，然后调用被扫订单结果查询API，查询当前订单的不同状态，决定下一步的操作"),
    PARAM_ERROR(400, "PARAM_ERROR", "参数错误", "根据错误提示，传入正确参数"),
    ORDER_NOT_EXIST(404, "ORDER_NOT_EXIST", "请求的资源不存在", "请商户检查需要查询的id或者请求URL是否正确"),
    CONTRACT_NOT_EXIST(403, "CONTRACT_NOT_EXIST", "签约协议不存在", "请检查签约协议号是否正确，是否已解约"),
    PHONE_NOT_EXIST(400, "PHONE_NOT_EXIST", "手机号不存在", "请检查手机号码是否正确"),
    SIGN_ERROR(401, "SIGN_ERROR", "签名验证失败", "请检查签名参数和方法是否都符合签名算法要求"),
    ACCOUNT_ERROR(403, "ACCOUNT_ERROR", "账号异常", "用户账号异常，无需更多操作"),
    SYSTEM_ERROR(500, "SYSTEM_ERROR", "系统错误", "5开头的状态码都为系统问题，请使用相同参数 稍后重新调用"),
    AUTH_CODE_INVALID(400, "AUTH_CODE_INVALID", "收银员扫描的不是微信支付的条码", "请扫描微信支付被扫条码/二维码"),
    FREQUENCY_LIMITED(429, "FREQUENCY_LIMITED", "频率超限", "请求量不要超过接口调用频率限制"),
    RATELIMIT_EXCEEDED(429, "RATELIMIT_EXCEEDED", "频率限制", "请降低频率后重试"),
    NO_AUTH(403, "NO_AUTH", "商户暂无权限使用此功能", "请开通商户号权限。请联系产品或商务申请"),
    RULE_LIMIT(403, "RULE_LIMIT", "业务规则限制", "因业务规则限制请求频率，请查看接口返回的详细信息"),
    AUTH_CODE_EXPIRE(403, "AUTH_CODE_EXPIRE", "用户的条码已经过期", "请收银员提示用户，请用户在微信上刷新条码，然后请收银员重新扫码。 直接将错误展示给收银员"),
    TRADE_ERROR(403, "TRADE_ERROR", "交易错误", "因业务原因交易失败，请查看接口返回的详细信息"),
    NOT_ENOUGH(403, "NOT_ENOUGH", "余额不足", "用户帐号余额不足，请用户充值或更换支付卡后再支付"),
    USER_NOT_EXIST(404, "USER_NOT_EXIST", "用户账户注销", "请检查用户账户是否正确"),
    ERROR(500, "ERROR", "业务错误", "该错误都会返回具体的错误原因，请根据实际返回做相应处理"),
    FREQUENCY_LIMIT_EXCEED(429, "FREQUENCY_LIMIT_EXCEED", "接口限频", "请降低调用频率"),
    CONTRACT_EXISTED(403, "CONTRACT_EXISTED", "协议已存在", "已开通自动扣费服务功能，无需重复开通"),
    USER_ACCOUNT_ABNORMAL(403, "USER_ACCOUNT_ABNORMAL", "用户账户异常", "该确认用户账号是否正常，商家可联系微信支付或让用户联系微信支付客服处理"),
    CONTRACT_ERROR(403, "CONTRACT_ERROR", "当前用户签约状态失效", "请通过查询用户接口核实签约状态"),
    REFUND_NOT_EXISTS(404, "REFUND_NOT_EXISTS", "订单号错误或订单状态不正确", "请检查订单号是否有误以及订单状态是否正确，如：未支付、已支付未退款"),
    CONTRACT_NOT_CONFIRMED(403, "CONTRACT_NOT_CONFIRMED", "二级商户未开启手动提现权限", "二级商户号提现权限已关闭，无法发起提现"),
    NO_STATEMENT_EXIST(400, "NO_STATEMENT_EXIST", "账单文件不存在", "请检查当前商户号是否在指定日期有交易或退款发生"),
    STATEMENT_CREATING(400, "STATEMENT_CREATING", "账单生成中", "请先检查当前商户号在指定日期内是否有成功的交易或退款，若有，则在T+1日上午8点后再重新下载"),
    MCH_NOT_EXISTS(404, "MCH_NOT_EXISTS", "商户号不存在", "请确认传入的商户号是否正确"),
    INVALID_REQUEST(400, "INVALID_REQUEST", "请求参数符合参数格式，但不符合业务规则", "请确认相同单号是否使用了不同的参数"),
    RESOURCE_NOT_EXISTS(404, "RESOURCE_NOT_EXISTS", "查询的资源不存在", "请检查查询资源的对应id是否填写正确"),
    RESOURCE_ALREADY_EXISTS(400, "RESOURCE_ALREADY_EXISTS", "用户已签约该商户，不可重复签约", "请通过查询用户接口获取用户的签约信息"),
    ALREADY_EXISTS(400, "ALREADY_EXISTS", "资源已存在", "尝试创建的资源已存在，无需重复创建"),
    USER_NOT_REGISTERED(400, "USER_NOT_REGISTERED", "服务未开通或账号未注册", "该用户尚未注册或开通当前服务，请开通后再试"),
    USER_NOT_EXISTS(404, "USER_NOT_EXISTS", "openid不正确", "请确认传入的openid是否正确"),
    ORDER_CLOSED(400, "ORDER_CLOSED", "订单已关闭", "当前订单已关闭，请重新下单"),
    ORDER_PAID(400, "ORDER_PAID", "订单已支付", "请确认该订单号是否重复支付，如果是新单，请使用新订单号提交"),
    ORDER_REVERSED(400, "ORDER_REVERSED", "订单已撤销", "当前订单状态为“订单已撤销”，请提示用户重新支付"),
    ORDERCLOSED(400, "ORDERCLOSED", "订单已关闭", "商户订单号异常，请重新下单支付"),
    ORDERPAID(400, "ORDERPAID", "订单已支付", "请确认该订单号是否重复支付，如果是新单，请使用新订单号提交"),
    ORDERREVERSED(400, "ORDERREVERSED", "订单已撤销", "当前订单状态为“订单已撤销”，请提示用户重新支付"),
    ACCOUNT_NOT_VERIFIED(403, "ACCOUNT_NOT_VERIFIED", "二级商户下行打款未成功", "二级商户号结算银行卡信息有误，修改后重试"),
    NOT_FOUND(404, "NOT_FOUND", "请求的资源不存在", "请商户检查需要查询的id或者请求URL是否正确"),

    ;

    private Integer statusCode;
    private String errorCode;
    private String description;
    private String resolution;

    WeChatStatusCode(Integer statusCode, String errorCode, String description, String resolution) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.description = description;
        this.resolution = resolution;
    }

}
