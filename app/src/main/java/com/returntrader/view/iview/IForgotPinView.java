package com.returntrader.view.iview;

import android.os.Bundle;

/**
 * Created by moorthy on 8/24/2017.
 */

public interface IForgotPinView extends IView {

    void navigateToVerifyForgotPin(Bundle bundle);

    String getRSAId();

    String getEmailId();
}
