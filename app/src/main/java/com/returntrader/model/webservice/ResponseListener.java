package com.returntrader.model.webservice;
/**
 * Created by Buvaneswaran on 06-12-2016.
 */
public interface ResponseListener {

    void onSuccess(String response, int flag);

    void onFailure(String response, int flag);
    void onFailure(Throwable throwable, int flag);

    void showDialog(String response, int flag);

    void showErrorDialog(String errorResponse, int flag);

    void showInternalServerErrorDialog(String errorResponse, int flag);

    void logOut(int flag);

}
