package com.jomkie.common.wechat;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jomkie
 * @since 2021-05-20 12:23:49
 * 微信支付请求基础构建类
 * 引用于微信文档 https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_4_1.shtml
 *    2021-1 版本，官方文档更新于 2020.05.26
 */
@Data
public class WeChatPayBuild {

    private JSONObject requestObj;
    private Map<String, Object> requestMap;

    /** 微信 Native 支付请求地址 */
    public static final String WECHAT_PAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/native";
    /** 请求后的回调地址 */
    public static final String WECHAT_NOTIFY_URL = "http://www.jomchen.top/net/test/remote";

    final String APPID = "appid";
    final String MCHID = "mchid";
    final String DESCRIPTION = "description";
    final String OUT_TRADE_NO = "out_trade_no";
    final String TIME_EXPIRE = "time_expire";
    final String ATTACH = "attach";
    final String NOTIFY_URL = "notify_url";
    final String GOODS_TAG = "goods_tag";
    final String AMOUNT = "amount";
        final String TOTAL = "total";
        final String CURRENCY = "currency";
    final String DETAIL = "detail";
        final String COST_PRICE = "cost_price";
        final String INVOICE_ID = "invoice_id";
        final String GOODS_DETAIL = "goods_detail";
            final String MERCHANT_GOODS_ID = "merchant_goods_id";
            final String WECHATPAY_GOODS_ID = "wechatpay_goods_id";
            final String GOODS_NAME = "goods_name";
            final String GOODS_QUANTITY = "quantity";
            final String GOODS_UNIT_PRICE = "unit_price";
    final String SCENE_INFO = "scene_info";
        final String PAYER_CLIENT_IP = "payer_client_ip";
        final String DEVICE_ID = "device_id";
        final String STORE_INFO = "store_info";
            final String STORE_ID = "id";
            final String STORE_NAME = "name";
            final String STORE_AREA_CODE = "area_code";
            final String STORE_ADDRESS = "address";
    final String SETTLE_INFO = "settle_info";
        final String PROFIT_SHARING = "profit_sharing";


    WeChatPayBuild() {
        this.requestObj = new JSONObject();
        this.requestMap = new HashMap<>();
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 15:43:38
     * @param appid 应用ID
     * @param mchid 直连商户的商户号
     * @param description 商品描述
     * @param outTradeNo 商户系统内部订单号
     * @param notifyUrl 回调地址
     * @param moneyTotal 订单总金额
     * @param moneyCurrency 货币类型
     */
    public static WeChatPayBuild buildParam(
            String appid,
            String mchid,
            String description,
            String outTradeNo,
            String notifyUrl,
            int moneyTotal,
            String moneyCurrency) {

        WeChatPayBuild weChatPayBuild = new WeChatPayBuild()
                .setAppid(appid)
                .setMchid(mchid)
                .setDescription(description)
                .setOutTradeNo(outTradeNo)
                .setNotifyUrl(notifyUrl)
                .setAmount(moneyTotal, moneyCurrency);

        return weChatPayBuild;
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 13:56:52
     * @param appid 微信生成的应用 ID，公众号场景下，需使用应用属性为公众号的 APPID
     */
    public WeChatPayBuild setAppid(String appid) {
        this.requestObj.put(APPID, appid);
        this.requestMap.put(APPID, appid);
        return this;
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 13:57:43
     * @param mchid 直连商户的商户号
     */
    public WeChatPayBuild setMchid(String mchid) {
        this.requestObj.put(MCHID, mchid);
        this.requestMap.put(MCHID, mchid);
        return this;
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 13:58:45
     * @param description 商品描述
     */
    public WeChatPayBuild setDescription(String description) {
        this.requestObj.put(DESCRIPTION, description);
        this.requestMap.put(DESCRIPTION, description);
        return this;
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 13:59:20
     * @param outTradeNo 商户订单号
     */
    public WeChatPayBuild setOutTradeNo(String outTradeNo) {
        this.requestObj.put(OUT_TRADE_NO, outTradeNo);
        this.requestMap.put(OUT_TRADE_NO, outTradeNo);
        return this;
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 14:0:31
     * @param timeExpire 订单失效时间，格式为 yyyy-MM-ddTHH:mm:ss，例如 2020-01-01T10:24:00
     */
    public WeChatPayBuild setTimeExpire(String timeExpire) {
        this.requestObj.put(TIME_EXPIRE, timeExpire);
        this.requestMap.put(TIME_EXPIRE, timeExpire);
        return this;
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 14:2:26
     * @param attach 附加数据
     */
    public WeChatPayBuild setAttach(String attach) {
        this.requestObj.put(ATTACH, attach);
        this.requestMap.put(ATTACH, attach);
        return this;
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 14:3:13
     * @param notifyUrl 回调地址
     */
    public WeChatPayBuild setNotifyUrl(String notifyUrl) {
        this.requestObj.put(NOTIFY_URL, notifyUrl);
        this.requestMap.put(NOTIFY_URL, notifyUrl);
        return this;
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 14:3:53
     * @param goodsTag 订单优惠标记
     */
    public WeChatPayBuild setGoodsTag(String goodsTag) {
        this.requestObj.put(GOODS_TAG, goodsTag);
        this.requestMap.put(GOODS_TAG, goodsTag);
        return this;
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 14:5:1
     * @param total 订单总金额，单位为分
     * @param currency 人民币，境内商户号仅支持人民币（CNY）
     *
     * 订单金额
     */
    public WeChatPayBuild setAmount(int total, String currency) {
        currency = Objects.isNull(currency) ? "CNY" : currency;

        JSONObject amount = new JSONObject()
                .fluentPut(TOTAL, total)
                .fluentPut(CURRENCY, currency);

        Map<Object, Object> amoutMap = new HashMap<>();
        amoutMap.put(TOTAL, total);
        amoutMap.put(CURRENCY, currency);

        this.requestObj.put(AMOUNT, amount);
        this.requestMap.put(AMOUNT, amoutMap);
        return this;
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 14:8:45
     * @param costPrice 见下列注释
     *    商户侧一张票订单可能被分多次支付，订单原价用于记录整张小票的交易金额
     *    当订单成年人与支付金额不相等，则不享受优惠
     *    该字段主要用于防止同一张小票分多次支付，以享受多次优惠的情况，正常支付订单不必上传 此参数
     * @param invoiceId 商家小票ID
     *
     * 优惠功能
     */
    public WeChatPayBuild setDetail(int costPrice, String invoiceId, List<JSONObject> goodsDetail, Map<Object, Object> goodsDetailMap) {
        JSONObject detail = new JSONObject();
        detail.fluentPut(COST_PRICE, costPrice)
                .fluentPut(INVOICE_ID, invoiceId)
                .fluentPut(GOODS_DETAIL, goodsDetail);
        this.requestObj.put(DETAIL, detail);

        Map<Object, Object> detailMap = new HashMap<>();
        detailMap.put(COST_PRICE, costPrice);
        detailMap.put(INVOICE_ID, invoiceId);
        detailMap.put(GOODS_DETAIL, goodsDetailMap);
        this.requestMap.put(DETAIL, detailMap);

        return this;
    }


    /**
     * @author Jomkie
     * @since 2021-05-20 14:29:28
     * @param merchantGoodsId 由半角大小写字母，数字，中划线，下划线中的一种或几种组成
     * @param wechatPayGoodsId 微信支付定义的统一商品编号（没有可不传）
     * @param goodsName 商户的实际名称
     * @param quantity 用户购买的数量
     * @param unitPrice 单位为分
     *
     * 构建单品列表
     */
    public JSONObject buildGoodsDetail(String merchantGoodsId, String wechatPayGoodsId, String goodsName, int quantity, int unitPrice) {
        JSONObject goodsDetail = new JSONObject()
                .fluentPut(MERCHANT_GOODS_ID, merchantGoodsId)
                .fluentPut(WECHATPAY_GOODS_ID, wechatPayGoodsId)
                .fluentPut(GOODS_NAME, goodsName)
                .fluentPut(GOODS_QUANTITY, quantity)
                .fluentPut(GOODS_UNIT_PRICE, unitPrice);
        return goodsDetail;
    }
    public Map<Object, Object> buildGoodsDetailMap(String merchantGoodsId, String wechatPayGoodsId, String goodsName, int quantity, int unitPrice) {
        Map<Object, Object> goodsDetailMap = new HashMap<>();
        goodsDetailMap.put(MERCHANT_GOODS_ID, merchantGoodsId);
        goodsDetailMap.put(WECHATPAY_GOODS_ID, wechatPayGoodsId);
        goodsDetailMap.put(GOODS_NAME, goodsName);
        goodsDetailMap.put(GOODS_QUANTITY, quantity);
        goodsDetailMap.put(GOODS_UNIT_PRICE, unitPrice);
        return goodsDetailMap;
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 14:18:15
     * @param payerClientIp 用户的客户端IP，支持 IPv4 和 IPv6 两种格式，示例值 14.23.150.211
     * @param deviceId 商户端设备号（门店号或收银设备ID）
     * @param storeId 商户侧门店编号
     * @param name 商户侧门店名称
     * @param areaCode 地区编码
     * @param address 详细的商户门店地址
     *
     * 场景信息
     */
    public WeChatPayBuild setSceneInfo(String payerClientIp, String deviceId, String storeId, String name, String areaCode, String address) {
        JSONObject storeInfo = new JSONObject()
                .fluentPut(STORE_ID, storeId)
                .fluentPut(STORE_NAME, name)
                .fluentPut(STORE_AREA_CODE, areaCode)
                .fluentPut(STORE_ADDRESS, address);

        JSONObject sceneInfo = new JSONObject()
                .fluentPut(PAYER_CLIENT_IP, payerClientIp)
                .fluentPut(DEVICE_ID, deviceId)
                .fluentPut(STORE_INFO, storeInfo);

        Map<Object, Object> storeInfoMap = new HashMap<>();
        storeInfoMap.put(STORE_ID, storeId);
        storeInfoMap.put(STORE_NAME, name);
        storeInfoMap.put(STORE_AREA_CODE, areaCode);
        storeInfoMap.put(STORE_ADDRESS, address);

        Map<Object, Object> sceneInfoMap = new HashMap<>();
        sceneInfoMap.put(PAYER_CLIENT_IP, payerClientIp);
        sceneInfoMap.put(DEVICE_ID, deviceId);
        sceneInfoMap.put(STORE_INFO, storeInfoMap);

        this.requestObj.put(SCENE_INFO, sceneInfo);
        return this;
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 14:26:5
     * @param profitSharing 是否指定分账
     *
     * 结算信息
     */
    public WeChatPayBuild setSettleInfo(boolean profitSharing) {
        JSONObject settleInfo = new JSONObject().fluentPut(PROFIT_SHARING, profitSharing);
        this.requestObj.put(SETTLE_INFO, settleInfo);

        Map<Object, Object> settleInfoMap = new HashMap<>();
        settleInfoMap.put(PROFIT_SHARING, profitSharing);
        this.requestMap.put(SETTLE_INFO, settleInfoMap);

        return this;
    }

}
