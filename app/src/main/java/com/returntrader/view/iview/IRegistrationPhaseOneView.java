package com.returntrader.view.iview;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.returntrader.model.dto.request.FullRegistrationRequest;

import java.util.Calendar;

/**
 * Created by moorthy on 10/28/2017.
 */

public interface IRegistrationPhaseOneView extends IView {

    void setPhoneNumber(String phoneNumber);

    void setSpinnerAdapter(ArrayAdapter<String> countryCodeAdapter, int type);

    void navigateToPhaseTwo(Bundle bundle);

    void setBirthDate(String date);

    void populateUserInfo(FullRegistrationRequest fullRegistrationRequest);
}

