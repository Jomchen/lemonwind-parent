package com.jomkie.common.wechat;

import com.jomkie.common.remote.RemoteApi;
import com.jomkie.common.remote.RemoteRequestObj;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public interface WeChatRequestBase<Request, Response> {


    /**
     * @author Jomkie
     * @since 2021-05-24 21:45:10
     * @param url 请求地址
     * @param httpMethod  请求方法
     * @param requestData 请求体
     * @return 构建的请求头
     *
     * 预构建请求头
     */
    HttpHeaders preHeaders(String url, HttpMethod httpMethod, Request requestData);

    /**
     * @author Jomkie
     * @since 2021-05-24 21:38:45
     * @param request 请求体
     * @param resultObj 返回结果
     * 请求后置处理
     */
    void postHandler(Request request, RemoteRequestObj<Response> resultObj);

    /**
     * @author Jomkie
     * @since 2021-05-24 21:39:41
     * @param url 请求地址
     * @param httpMethod  请求方法
     * @param requestData 请求体
     * @param cla 返回类
     * @param remoteApi 请求执行类
     * @return 请求的返回结果
     *
     * 微信扫码支付的请求
     */
    default RemoteRequestObj<Response> nativePay(String url, HttpMethod httpMethod, Request requestData, Class<Response> cla, RemoteApi remoteApi) {
        HttpHeaders httpHeaders = preHeaders(url, httpMethod, requestData);
        RemoteRequestObj<Response> result = remoteApi.execute(url, httpMethod, httpHeaders, requestData, cla);
        postHandler(requestData, result);
        return result;
    }

}
