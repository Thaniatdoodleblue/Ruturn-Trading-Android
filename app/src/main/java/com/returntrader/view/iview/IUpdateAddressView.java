package com.returntrader.view.iview;

import com.returntrader.model.dto.request.FullRegistrationRequest;

/**
 * Created by moorthy on 12/1/2017.
 */

public interface IUpdateAddressView extends IView {


    void addressUpdatedSuccessfully();

    void setLocation(FullRegistrationRequest mLocationInfo);

    void populateUserInformation(FullRegistrationRequest fullRegistrationRequest);

}
