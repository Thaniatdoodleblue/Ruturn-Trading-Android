package com.returntrader.permissions;

import android.content.Context;

/**
 * Created by guru on 2/27/2016.
 */
public interface IPermissionHandler {

    int PERMISSIONS_REQUEST_ACCESS_CONTACT = 40;

    int PERMISSIONS_REQUEST_CAMERA = 41;

    int PERMISSIONS_REQUEST_RECEIVE_SMS = 42;

    void callFetchContactPermissionHandler();

    void callCameraPermissionHandler();

    void callSmsPermissionHandler();

    void showPermissionExplainDialog(Context context, String message);


}
