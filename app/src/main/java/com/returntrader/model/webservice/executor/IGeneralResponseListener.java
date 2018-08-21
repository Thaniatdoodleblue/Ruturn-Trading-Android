package com.returntrader.model.webservice.executor;

/**
 * Created by Karthikeyan on 17-07-2017
 */

public interface IGeneralResponseListener<T>{

    void onSuccess(T mResponse, int flag);

    void onFailure(String response, int flag);

    void onFailure(Throwable throwable, int flag);

    void showDialog(String response, int flag);

    void showErrorDialog(String errorResponse, int flag);

    void showInternalServerErrorDialog(String errorResponse, int flag);

    void logOut(int flag);
}

