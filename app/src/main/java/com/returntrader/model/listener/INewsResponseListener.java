package com.returntrader.model.listener;

/**
 * Created by Karthikeyan on 18-07-2017
 */

public interface INewsResponseListener<T> {


    void onSuccess(T mResponse, int flag);

    void onFailure(String response, int flag);

    void onFailure(Throwable throwable, int flag);
    void showDialog(String response, int flag);

    void showErrorDialog(String errorResponse, int flag);

    void showInternalServerErrorDialog(String errorResponse, int flag);

    void logOut(int flag);
}
