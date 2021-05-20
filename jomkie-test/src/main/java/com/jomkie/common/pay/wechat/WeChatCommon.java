package com.jomkie.common.pay.wechat;

/**
 * @author Jomkie
 * @since 2021-05-20 12:23:49
 * 微信基础信息
 */
public class WeChatCommon {

    String wechatPayScanningUrl = "https://api.mch.weixin.qq.com/v3/pay/transactions/native";
    String requestType = "POST";

    String appid = "appid";
    String mchid = "mchid";
    String description = "description";
    String out_trade_no = "out_trade_no";
    String time_expire = "time_expire";
    String attach = "attach";
    String notify_url = "notify_url";
    String goods_tag = "goods_tag";
    String amount = "amount";
        String total = "total";
        String currency = "currency";
    String detail = "detail";
        String cost_price = "cost_price";
        String invoice_id = "invoice_id";
        String goods_detail = "goods_detail";
            String merchant_goods_id = "merchant_goods_id";
            String wechatpay_goods_id = "wechatpay_goods_id";
            String goods_name = "goods_name";
            String quantity = "quantity";
            String unit_price = "unit_price";
    String scene_info = "scene_info";
        String payer_client_ip = "payer_client_ip";
        String device_id = "device_id";
        String store_info = "store_info";
            String id = "id";
            String name = "name";
            String area_code = "area_code";
            String address = "address";
    String settle_info = "settle_info";
        String profit_sharing = "profit_sharing";
}
