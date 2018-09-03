package com.github.httpclient;

/**
 * @data 2018-09-03
 * @desc 封装请求
 */

public interface IHttpService {

    void setUrl(String url);

    void setRequestData(byte[] requestData);

    void execute();

    //设置两个接口之间的关系
    void setHttpCallBack(IHttpListener iHttpListener);

}
