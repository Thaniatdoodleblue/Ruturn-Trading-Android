package com.returntrader.permissions;


import com.returntrader.view.iview.IView;

public interface PermissionProducer extends IView {

    void onReceivedPermissionStatus(int code, boolean isGrated);
}
