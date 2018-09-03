package com.github.httpclient;

/**
 * @data 2018-09-03
 * @desc 回调调用层
 */

public interface IDataListener<M> {

    void onSuccess(M m);
    void onFailure();

}
