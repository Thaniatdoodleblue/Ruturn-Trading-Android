package com.returntrader.utils;

public interface IPermissionHandler {

    int PERMISSIONS_REQUEST_CAMERA = 40;
    int PERMISSIONS_REQUEST_RECEVIE_SMS = 41;
    int PERMISSIONS_REQUEST_CAMERA_AND_STORAGE = 42;
    int PERMISSIONS_REQUEST_GALERY_AND_STORAGE = 43;
    int PERMISSIONS_REQUEST_LOCATION = 44;

    void callSmsPermissionHandler();

    void callCameraPermissionHandler();

    void callCameraAndStoragePermissionHandler();

    void callStoragePermissionHandler();

    void callLocationPermissionHandler();
}
