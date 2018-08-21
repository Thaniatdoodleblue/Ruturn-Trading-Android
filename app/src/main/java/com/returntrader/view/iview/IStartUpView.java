package com.returntrader.view.iview;

import android.os.Bundle;

/**
 * Created by moorthy on 9/14/2017.
 */

public interface IStartUpView extends IView {

    void navigateToHome(Bundle bundle);

    void navigateToLauncher(Bundle bundle);

    void navigateToUserLogin();

    void startDelayPriceUpdateService();
}
