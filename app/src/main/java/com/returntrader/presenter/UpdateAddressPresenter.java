package com.returntrader.presenter;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.library.Log;
import com.returntrader.model.dto.request.AddressRequest;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.UpdatePreferenceModel;
import com.returntrader.presenter.ipresenter.IUpdateAddressPresenter;
import com.returntrader.view.iview.IUpdateAddressView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by moorthy on 10/28/2017.
 */

public class UpdateAddressPresenter extends BasePresenter implements IUpdateAddressPresenter {


    private IUpdateAddressView iUpdateAddressView;
    private FullRegistrationRequest mFullRegistrationRequest;
    private AppConfigurationManager mAppConfigurationManager;
    private UpdatePreferenceModel mUpdateAddressModel;

    public UpdateAddressPresenter(IUpdateAddressView iView) {
        super(iView);
        this.iUpdateAddressView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        this.mUpdateAddressModel = new UpdatePreferenceModel(updateAddressResponseListener);
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        String userInfoFull = mAppConfigurationManager.getUserInfo();
        if (!(TextUtils.isEmpty(userInfoFull))) {
            mFullRegistrationRequest = iUpdateAddressView.getCodeSnippet()
                    .getObjectFromJson(mAppConfigurationManager.getUserInfo(), FullRegistrationRequest.class);
            if (mFullRegistrationRequest != null) {
                iUpdateAddressView.populateUserInformation(mFullRegistrationRequest);
            }
        }
    }


    @Override
    public void onClickSubmit(AddressRequest addressRequest) {
        if (mFullRegistrationRequest != null) {
            addressRequest.setUserId(mAppConfigurationManager.getUserId());
            addressRequest.setComplexApartmentNumberOne(addressRequest.getComplexApartmentNumberOne());
            addressRequest.setComplexApartmentNumberTwo(addressRequest.getComplexApartmentNumberTwo());
            addressRequest.setStreetNumber(addressRequest.getStreetNumber());
            addressRequest.setStreet(addressRequest.getStreet());
            addressRequest.setLocation(addressRequest.getStreet());
            addressRequest.setArea(addressRequest.getArea());
            addressRequest.setCity(addressRequest.getCity());
            addressRequest.setCountryOfResidenceId(mFullRegistrationRequest.getCountryOfResidenceId());
            addressRequest.setProvince(mFullRegistrationRequest.getProvince());
            if (iUpdateAddressView.getCodeSnippet().hasNetworkConnection()) {
                iUpdateAddressView.showProgressbar();
                mUpdateAddressModel.updateAddress(addressRequest);
            } else {
                iUpdateAddressView.showNetworkMessage();
            }
        }
    }

    /***/
    private void getAddress(Place place) {
        Geocoder geocoder = new Geocoder(iUpdateAddressView.getActivity(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 5);
            if (addresses.size() > 0) {

                String streetNo = addresses.get(0).getSubThoroughfare();
                String streetName = addresses.get(0).getThoroughfare();
                String city = addresses.get(0).getLocality();
                String area = addresses.get(0).getSubLocality();
                String postalCode = addresses.get(0).getPostalCode();
                String fullAddress = place.getAddress().toString();

                FullRegistrationRequest mLocationInfo = new FullRegistrationRequest();
                mLocationInfo.setStreetNumber(TextUtils.isEmpty(streetNo) ? "" : streetNo);
                mLocationInfo.setStreet(TextUtils.isEmpty(streetName) ? "" : streetName);
                mLocationInfo.setArea(TextUtils.isEmpty(area) ? "" : area);
                mLocationInfo.setCity(TextUtils.isEmpty(city) ? "" : city);
                mLocationInfo.setPinCode(TextUtils.isEmpty(postalCode) ? "" : postalCode);

                iUpdateAddressView.setLocation(mLocationInfo);
            }
        } catch (IOException e) {
            iUpdateAddressView.showMessage(e.getMessage());
            e.printStackTrace();
        }
    }

    /***/
    private IBaseModelListener<BaseResponse> updateAddressResponseListener = new IBaseModelListener<BaseResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, BaseResponse response) {
            iUpdateAddressView.dismissProgressbar();
            if (response != null) {
                // address updated so remove cards.
                mAppConfigurationManager.setAddressCompletedStatus(true);
                iUpdateAddressView.showMessage("Address updated successfully!");
                iUpdateAddressView.addressUpdatedSuccessfully();
            } else {
                iUpdateAddressView.showMessage("Given address inputs are incorrect!. Please choose valid location with street name.");
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iUpdateAddressView.dismissProgressbar();
            iUpdateAddressView.showMessage("Given address inputs are incorrect!. Please choose valid location with street name.");
        }
    };

    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCodes.PLACE_AUTOCOMPLETE_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(iUpdateAddressView.getActivity(), data);
                    getAddress(place);
                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(iUpdateAddressView.getActivity(), data);
                    Log.i(TAG, status.getStatusMessage());
                }
                break;
        }
    }
}
