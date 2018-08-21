package com.returntrader.view.iview;

import android.os.Bundle;

public interface IForgotPinVerifyView extends IView {

    void navigateToResetPassword(Bundle bundle);

    void setEmailText(String emailText);

    void enableOrDisableNext(boolean b);
}
