package com.returntrader.view.iview;

import android.os.Bundle;

import com.returntrader.model.dto.request.FullRegistrationRequest;

/**
 * Created by moorthy on 10/28/2017.
 */

public interface IRegistrationPhaseTwoView extends IView {

    void registrationSuccessful();

    void setLocation(FullRegistrationRequest mLocationInfo);

    void populateUserInformation(FullRegistrationRequest fullRegistrationRequest);

    void navigateToPhaseThree(Bundle bundle);

    void postResult(Bundle bundle);
}
