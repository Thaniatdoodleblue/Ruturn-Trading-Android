package com.returntrader.view.iview;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.returntrader.model.dto.request.FullRegistrationRequest;

/**
 * Created by moorthy on 12/8/2017.
 */

public interface IEditProfilePhaseOneView extends IView {

    void setUserData(FullRegistrationRequest fullRegistrationRequest);

    void setBirthDate(String birthDate);

    void navigateToPhaseTwo(Bundle bundle);
}
