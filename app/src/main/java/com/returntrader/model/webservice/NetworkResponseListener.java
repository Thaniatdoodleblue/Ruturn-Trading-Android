package com.returntrader.model.webservice;


import com.returntrader.library.CustomException;

/**
 * Created by anand_android on 9/28/2016.
 */
public interface NetworkResponseListener<T> {

    void onSuccess(long taskId, T mResponse);

    void onFailure(long taskId, Throwable mThrowable);

    void oFailure(long taskId, CustomException e);

}
