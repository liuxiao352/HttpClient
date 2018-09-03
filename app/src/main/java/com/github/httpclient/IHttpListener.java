package com.github.httpclient;

import java.io.InputStream;

/**
 * @data 2018-09-03
 * @desc 封装响应
 */

public interface IHttpListener{

    //接收上一个接口的结果
    void onSuccess(InputStream inputStream);
    void onFailure();

}
