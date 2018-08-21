package com.returntrader.view.iview;

public interface PermissionProducer extends IView {
    void onReceivedPermissionStatus(int code, boolean isGranted);
}