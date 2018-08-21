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
import com.returntrader.library.Log;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.presenter.ipresenter.IRegistrationPhaseTwoPresenter;
import com.returntrader.view.iview.IRegistrationPhaseTwoView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by moorthy on 10/28/2017.
 */

public class RegistrationPhaseTwoPresenter extends BasePresenter implements IRegistrationPhaseTwoPresenter {


    private IRegistrationPhaseTwoView iRegistrationPhaseTwoView;
    private FullRegistrationRequest mFullRegistrationRequest;
    private AppConfigurationManager mAppConfigurationManager;

    public RegistrationPhaseTwoPresenter(IRegistrationPhaseTwoView iView) {
        super(iView);
        this.iRegistrationPhaseTwoView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            mFullRegistrationRequest = bundle.getParcelable(Constants.BundleKey.BUNDLE_REGISTER_REQUEST);
            iRegistrationPhaseTwoView.populateUserInformation(mFullRegistrationRequest);
        }
    }


    @Override
    public void onClickSubmit(FullRegistrationRequest fullRegistrationRequest, boolean isSaveEnabled) {
        if (mFullRegistrationRequest != null) {
            mFullRegistrationRequest.setGenderId(fullRegistrationRequest.getGenderId());
            mFullRegistrationRequest.setComplexApartmentNumber(fullRegistrationRequest.getComplexApartmentNumber());
            mFullRegistrationRequest.setComplexApartmentName(fullRegistrationRequest.getComplexApartmentName());
            mFullRegistrationRequest.setStreetNumber(fullRegistrationRequest.getStreetNumber());
            mFullRegistrationRequest.setLocation(fullRegistrationRequest.getLocation());//Street name
            mFullRegistrationRequest.setStreet(fullRegistrationRequest.getLocation());//Street name
            mFullRegistrationRequest.setArea(fullRegistrationRequest.getArea());//Suburb
            mFullRegistrationRequest.setCity(fullRegistrationRequest.getCity());
            mFullRegistrationRequest.setPinCode(fullRegistrationRequest.getPinCode());

            if (isSaveEnabled) {
                String userInfo = iRegistrationPhaseTwoView.getCodeSnippet()
                        .getJsonStringFromObject(mFullRegistrationRequest, FullRegistrationRequest.class);
                Log.d(TAG, "userInfo : " + userInfo);
                mAppConfigurationManager.setUserInfo(userInfo);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.BundleKey.BUNDLE_REGISTER_REQUEST, mFullRegistrationRequest);
                iRegistrationPhaseTwoView.registrationSuccessful();
            } else {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.BundleKey.BUNDLE_REGISTER_REQUEST, mFullRegistrationRequest);
                iRegistrationPhaseTwoView.navigateToPhaseThree(bundle);
            }
        } else {
            iRegistrationPhaseTwoView.showMessage("Problem with full registration");
        }
    }


    /***/
    private void getAddress(Place place) {
        Geocoder geocoder = new Geocoder(iRegistrationPhaseTwoView.getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 5);
            if (addresses.size() > 0) {
                String streetNo = addresses.get(0).getSubThoroughfare();
                String streetName = addresses.get(0).getThoroughfare();
                String city = addresses.get(0).getLocality();
                String area = addresses.get(0).getSubLocality();
                String postalCode = addresses.get(0).getPostalCode();
                String fullAddress = place.getAddress().toString();

                Log.e("getSubThoroughfare", "" + addresses.get(0).getSubThoroughfare());
                Log.e("getAddressLine", "" + addresses.get(0).getAddressLine(0));
                Log.e("getLocality", "" + addresses.get(0).getLocality());
                Log.e("getAdminArea", "" + addresses.get(0).getAdminArea());
                Log.e("getPostalCode", "" + addresses.get(0).getPostalCode());
                Log.e("getCountryCode", "" + addresses.get(0).getCountryCode());
                Log.e("getCountryName", "" + addresses.get(0).getCountryName());
                Log.e("getFeatureName", "" + addresses.get(0).getFeatureName());
                Log.e("getPhone", "" + addresses.get(0).getPhone());
                Log.e("getPremises", "" + addresses.get(0).getPremises());
                Log.e("getSubAdminArea", "" + addresses.get(0).getSubAdminArea());
                Log.e("getSubLocality", "" + addresses.get(0).getSubLocality());
                Log.e("getThoroughfare", "" + addresses.get(0).getThoroughfare());
                Log.e("getUrl", "" + addresses.get(0).getUrl());
                Log.e("getLatitude", "" + addresses.get(0).getLatitude());
                Log.e("getLocale", "" + addresses.get(0).getLocale());
                Log.e("getLongitude", "" + addresses.get(0).getLongitude());
                Log.e("getMaxAddressLineIndex", "" + addresses.get(0).getMaxAddressLineIndex());

                FullRegistrationRequest mLocationInfo = new FullRegistrationRequest();
                mLocationInfo.setStreetNumber(TextUtils.isEmpty(streetNo) ? "" : streetNo);
                mLocationInfo.setStreet(TextUtils.isEmpty(streetName) ? "" : streetName);
                mLocationInfo.setArea(TextUtils.isEmpty(area) ? "" : area);
                mLocationInfo.setCity(TextUtils.isEmpty(city) ? "" : city);
                mLocationInfo.setPinCode(TextUtils.isEmpty(postalCode) ? "" : postalCode);
                iRegistrationPhaseTwoView.setLocation(mLocationInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
            iRegistrationPhaseTwoView.showMessage(e.getMessage());
        }
    }

    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCodes.PLACE_AUTOCOMPLETE_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(iRegistrationPhaseTwoView.getActivity(), data);
                    getAddress(place);
                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(iRegistrationPhaseTwoView.getActivity(), data);
                    Log.i(TAG, status.getStatusMessage());
                }
                break;
            case Constants.RequestCodes.NAVIGATE_REGISTRATION_PHASE_THREE:
                if (resultCode == Activity.RESULT_OK) {
                    iRegistrationPhaseTwoView.registrationSuccessful();
                } else if (resultCode == Activity.RESULT_FIRST_USER) {
                    iRegistrationPhaseTwoView.registrationSuccessful();
                }
                break;
        }

    }
}
