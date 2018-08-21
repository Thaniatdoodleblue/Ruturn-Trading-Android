package com.returntrader.view.iview;

import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * Created by moorthy on 8/24/2017.
 */

public interface IBasicInfoView extends IView {

    void navigateToVerifyOtp(Bundle bundle);

    void setUpTitleSpinner(ArrayAdapter<String> spinnerArrayAdapter);
}
