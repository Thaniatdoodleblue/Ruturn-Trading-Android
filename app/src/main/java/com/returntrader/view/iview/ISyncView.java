package com.returntrader.view.iview;

import android.os.Bundle;

/**
 * Created by guru on 8/19/2017.
 */

public interface ISyncView extends IView {

    void navigateToHome(Bundle bundle);

    void setNextButtonVisibility();

    void startDelayPriceUpdateService();
}
