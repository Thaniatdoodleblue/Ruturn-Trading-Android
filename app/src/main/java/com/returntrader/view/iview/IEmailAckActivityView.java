package com.returntrader.view.iview;

import android.os.Bundle;

/**
 * Created by Dell on 19-05-2017.
 */

public interface IEmailAckActivityView extends IView {

    void setEmailText(String emailText);

    void displayEmailVerificationDone();

    void navigateToPinLock(Bundle bundle);


}
