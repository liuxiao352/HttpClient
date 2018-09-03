package com.github.httpclient;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.github.bean.TestBean;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @data 2018-09-03
 * @desc
 */

public class JsonHttpListener<M> implements IHttpListener{

    Class<M> responceClass;
    IDataListener<M> mMIDataListener;

    //切换线程
    Handler mHandler = new Handler(Looper.getMainLooper());

    public JsonHttpListener(Class<M> responceClass, IDataListener<M> MIDataListener) {
        this.responceClass = responceClass;
        mMIDataListener = MIDataListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String content = getContent(inputStream);
        Gson gson = new Gson();
        //json 字符串转换成对象
        final M responce = gson.fromJson(content, responceClass);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //把结果传送到调用层
                if (mMIDataListener != null){
                    mMIDataListener.onSuccess(responce);
                }
            }
        });
    }

    @Override
    public void onFailure() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mMIDataListener != null) {
                    mMIDataListener.onFailure();
                }
            }
        });

    }

    private String getContent(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
