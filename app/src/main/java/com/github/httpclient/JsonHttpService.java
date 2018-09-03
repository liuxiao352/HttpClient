package com.github.httpclient;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @data 2018-09-03
 * @desc
 */

public class JsonHttpService implements IHttpService{

    String url;
    private byte[] requestData;
    private IHttpListener mIHttpListener;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }

    @Override
    public void execute() {
        HttpUrlconnPost();
    }

    private HttpURLConnection mHttpURLConnection;

    private void HttpUrlconnPost() {
        URL url;
        try {
            url = new URL(this.url);
            mHttpURLConnection = (HttpURLConnection) url.openConnection();
            mHttpURLConnection.setRequestMethod("GET");
            //连接
            mHttpURLConnection.connect();
            //得到响应码
            int responseCode = mHttpURLConnection.getResponseCode();
            Log.e("test", "responseCode : " + responseCode);
            if(responseCode == HttpURLConnection.HTTP_OK){
                //得到响应流
                InputStream inputStream = mHttpURLConnection.getInputStream();
                mIHttpListener.onSuccess(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIHttpListener.onFailure();
        }finally {
            mHttpURLConnection.disconnect();
        }
    }

    @Override
    public void setHttpCallBack(IHttpListener iHttpListener) {
        mIHttpListener = iHttpListener;
    }
}
