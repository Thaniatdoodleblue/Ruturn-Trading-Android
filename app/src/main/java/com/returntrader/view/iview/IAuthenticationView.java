package com.returntrader.view.iview;

import android.os.Bundle;

/**
 * Created by moorthy on 9/14/2017.
 */

public interface IAuthenticationView extends IView {


    void setFirstEnteredPin(String value);

    void setToolBarTitle(String toolBarTitle);

    void navigateToFingerPrintOrOTP(Bundle bundle);

    void closeScreen();
}
