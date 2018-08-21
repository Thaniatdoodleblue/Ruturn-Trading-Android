package com.returntrader.model.webservice.executor;

/**
 * Created by Guru karthik on 26-11-2016.
 */

public interface ITaskManager<T> {

    void onSuccess(T data);

    void onFailure();
}
