package com.returntrader.view.iview;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.returntrader.model.dto.request.FullRegistrationRequest;

/**
 * Created by moorthy on 10/28/2017.
 */

public interface IRegistrationPhaseThreeView extends IView {

    void registrationSuccessful();


    void populateUserInformation(FullRegistrationRequest fullRegistrationRequest);

    void postResult(Bundle bundle);

    void setSpinnerAdapter(ArrayAdapter<String> spinnerAdapter, int type);
}
