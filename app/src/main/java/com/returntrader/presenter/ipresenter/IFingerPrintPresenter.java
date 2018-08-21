package com.returntrader.presenter.ipresenter;

import android.os.Bundle;

/**
 * Created by moorthy on 11/13/2017.
 */

public interface IFingerPrintPresenter extends IPresenter {

    int getEntryPoint();

    void setFingerPrintPinEnabled(boolean isEnabled);

    Bundle getBundleData();
}
