package com.lemonwind.test.common.wechat;

import com.alibaba.fastjson.JSONObject;
import com.lemonwind.test.common.LemonException;
import com.lemonwind.test.common.remote.RemoteRequestObj;
import com.lemonwind.test.common.wechat.action.WeChatPlatformCertification;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class WeChatAuthentication {

    /** 商户的appid */
    public static final String APPID = "appid";
    /** 商户号 */
    public static final String MERCHANTID = "merchantId";
    /** 请求模式 */
    public static final String SCHEMA = "WECHATPAY2-SHA256-RSA2048";
    /** 商户证书文件路径 */
    public static final String CERTIFICATION_PATH = "/home/lemonwind/work/apiclient_cert.pem";
    /** 商户私钥文件路径 */
    public static final String PRIVATEKEY_PATH = "/home/jomkie/work/apiclient_key.pem";

    /**
     * @author Jomkie
     * @since 2021-05-24 22:58:17
     * @param currentDate 请求时间（会作为签名的条件）
     * @param requestData 请求体
     * @param httpMethod 请求方式
     * @param requestUrl 请求地址
     * @param nonceStr 随机字符串
     * 获取基本的请求头
     */
    public HttpHeaders getBaseHttpHeaders(Date currentDate, Object requestData, HttpMethod httpMethod, String requestUrl, String nonceStr) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBodyJson = JSONObject.toJSONString(requestData);
        String authorization = getAuthorization(
                nonceStr,
                currentDate,
                httpMethod,
                requestUrl,
                requestBodyJson
        );
        headers.set("Authorization", authorization);

        return headers;
    }

    /**
     * @author Jomkie
     * @since 2021-05-24 23:11:19
     * @param nonceStr 随机字符串
     * @param date 请求时间（会作为签名的条件）
     * 获取平台支付证书
     */
    public HttpHeaders getPlatformCertificationsHeaders(String nonceStr, Date date) {
        HttpMethod httpMethod = HttpMethod.GET;
        String authorization = getAuthorization(nonceStr, date, httpMethod, WeChatPlatformCertification.WECHAT_REQUEST_URL, null);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String userAgent = String.format("WeChatPay-Jomkie-%s", "Linux");
        headers.set("User-Agent", userAgent);
        headers.set("Authorization", authorization);

        return headers;
    }



    /**
     * @author Jomkie
     * @since 2021-05-22 23:45:48
     * @param nonceStr 随机字符串
     * @param currentDate 时间戳，会被处理成秒作为签名条件
     * @param httpMethod 方法类型：例如：POST, GET
     * @param requestUrl 请求地址
     * @param body 请求体的 JSON 字符串
     *  获取 authentication
     */
    public String getAuthorization(String nonceStr, Date currentDate, HttpMethod httpMethod, String requestUrl, String body) {
        String token = getToken(nonceStr, currentDate, httpMethod.name(), requestUrl, body);
        return new StringBuilder().append(SCHEMA).append(" ").append(token).toString(); // TODO 这里也有疑问，官方给的是换行还是空格还是空字符串
    }

    public boolean verifySignature(RemoteRequestObj<String> remoteRequestObj) {
        HttpHeaders httpHeaders = remoteRequestObj.getHttpHeaders();
        Map<String, String> headerMap = httpHeaders.toSingleValueMap();
        String serial = headerMap.get("Wechatpay-Serial");

        String signature = headerMap.get("Wechatpay-Signature");
        String timestampStr = headerMap.get("Wechatpay-Timestamp");
        String nonceStr = headerMap.get("Wechatpay-Nonce ");
        String body = remoteRequestObj.getData();

        if (Strings.isEmpty(body)) { body = ""; } // TODO GET请求或没有请求体时，微信文档未表明清楚：是 空格 还是 空字符串，此处暂时表示空字符串
        String preSignatureStr = new StringBuilder()
                .append(timestampStr).append("\n")
                .append(nonceStr).append("\n")
                .append(body).append("\n")
                .toString();
        byte[] decodeSignatureByte = Base64.getDecoder().decode(signature);
        String responseSignature = new String(decodeSignatureByte); // 编码？

        // TODO 逻辑未完成
        return true;
    }

    public String getToken(String nonceStr, Date currentDate, String method, String requestUrl, String body) {
        HttpUrl httpUrl = HttpUrl.parse(requestUrl);
        long timestamp = currentDate.getTime() / 1000;
        String message = buildMessage(method, httpUrl, timestamp, nonceStr, body);
        String signature = sign(message.getBytes(StandardCharsets.UTF_8));
        String serialNo = getCertificateNumber();

        return new StringBuilder()
                .append("mchid=\"").append(MERCHANTID).append("\",")
                .append("nonce_str=\"").append(nonceStr).append("\",")
                .append("timestamp=\"").append(timestamp).append("\",")
                .append("serial_no=\"").append(serialNo).append("\",")
                .append("signature=\"").append(signature).append("\"")
                .toString();

        /*return "mchid=\"" + MERCHANTID + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + serialNo + "\","
                + "signature=\"" + signature + "\"";*/
    }

    String sign(byte[] message) {
        Signature sign;
        try {
            sign = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            throw new LemonException("SHA256withRSA签名失败", e);
        }

        PrivateKey privateKey = getPrivateKey();
        try {
            sign.initSign(privateKey);
        } catch (InvalidKeyException e) {
            throw new LemonException("微信签名初始化私钥失败", e);
        }
        try {
            sign.update(message);
        } catch (SignatureException e) {
            throw new LemonException("微信签名处理失败", e);
        }

        byte[] signature;
        try {
            signature = sign.sign();
        } catch (SignatureException e) {
            throw new LemonException("微信签名处理失败", e);
        }
        return Base64.getEncoder().encodeToString(signature);
    }

    String buildMessage(String method, HttpUrl httpUrl, long timestamp, String nonceStr, String body) {
        String canonicalUrl = httpUrl.encodedPath();
        if (httpUrl.encodedQuery() != null) {
            canonicalUrl += "?" + httpUrl.encodedQuery();
        }

        if (Strings.isEmpty(body)) { body = ""; } // TODO GET请求或没有请求体时，微信文档未表明清楚：是 空格 还是 空字符串，此处暂时表示空字符串

        return new StringBuilder()
                .append(method).append("\n")
                .append(canonicalUrl).append("\n")
                .append(timestamp).append("\n")
                .append(nonceStr).append("\n")
                .append(body).append("\n")
                .toString();

        /*return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";*/
    }

    /**
     * @author Jomkie
     * @since 2021-05-23 2:11:43
     * 获取商户私钥
     */
    PrivateKey getPrivateKey() {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(PRIVATEKEY_PATH);
        } catch (FileNotFoundException e) {
            throw new LemonException("获取商户私钥失败", e);
        }

        PrivateKey privateKey = PemUtil.loadPrivateKey(inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            log.error("关闭流失败", e);
        }
        return privateKey;
    }

    /**
     * @author Jomkie
     * @since 2021-05-23 2:11:14
     * 获取商户证书序列号
     */
    String getCertificateNumber() {
        return getCertificate().getSerialNumber().toString(16);
    }

    /**
     * @author Jomkie
     * @since 2021-05-23 2:11:32
     * 商户证书
     */
    X509Certificate getCertificate() {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(CERTIFICATION_PATH);
        } catch (FileNotFoundException e) {
            throw new LemonException("加载商户证书失败", e);
        }
        X509Certificate certificate = PemUtil.loadCertificate(inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            log.error("关闭流失败", e);
        }

        log.warn("输出证书信息：{}\n", certificate);
        log.warn("证书序列号：{}\n", certificate.getSerialNumber().toString(16));
        log.warn("版本号：{}\n", certificate.getVersion());
        log.warn("签发者：{}\n", certificate.getIssuerDN());
        log.warn("有效起始日期：{}\n", certificate.getNotBefore());
        log.warn("有效终止日期：{}\n", certificate.getNotAfter());
        log.warn("主体名：{}\n", certificate.getSubjectDN());
        log.warn("签名算法：{}\n", certificate.getSigAlgName());
        log.warn("签名：{}\n", certificate.getSignature().toString());

        return certificate;
    }

}
