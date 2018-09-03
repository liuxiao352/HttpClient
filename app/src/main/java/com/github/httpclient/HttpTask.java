package com.github.httpclient;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * @data 2018-09-03
 * @desc
 */

public class HttpTask<T> implements Runnable{

    private IHttpService mIHttpService;
    private IHttpListener mIHttpListener;

    public<T> HttpTask(T requestInfo, String url, IHttpService IHttpService, IHttpListener IHttpListener) {
        mIHttpService = IHttpService;
        mIHttpListener = IHttpListener;
        mIHttpService.setUrl(url);
        mIHttpService.setHttpCallBack(IHttpListener);
        if (requestInfo != null) {
            Gson gson = new Gson();
            String requestContent = gson.toJson(requestInfo);
            try {
                IHttpService.setRequestData(requestContent.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        mIHttpService.execute();
    }
}
