package com.returntrader.view.iview;

import android.os.Bundle;

public interface IOtpCodeVerifyView extends IView{

    void navigateToRegistrationFirstActivity();

    void navigateToEmailEntry(Bundle bundle);

    void setPhoneNumberText(String phoneNumber);

    void setOtp(String otp);

    void navigateToHome(Bundle bundle);

    void resetPin();
}
