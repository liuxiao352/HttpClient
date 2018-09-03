package com.github.httpclient;

/**
 * @data 2018-09-03
 * @desc
 */

public class Volley {

    public static<T, M> void sendJsonRequest(T requestInfo, String url, Class<M> responce, IDataListener<M> dataListener){
        IHttpService httpService = new JsonHttpService();
        IHttpListener httpListener = new JsonHttpListener(responce, dataListener);
        HttpTask<T> httpTask = new HttpTask<>(null, url, httpService, httpListener);
        ThreadPoolManager.getOurInstance().execute(httpTask);
    }

}
