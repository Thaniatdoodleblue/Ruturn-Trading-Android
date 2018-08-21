package com.returntrader.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.widget.ArrayAdapter;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.model.common.BirthPlace;
import com.returntrader.model.common.ConfigData;
import com.returntrader.model.common.Country;
import com.returntrader.model.common.PhoneNumber;
import com.returntrader.model.common.Province;
import com.returntrader.model.common.UserInfo;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.presenter.ipresenter.IRegistrationPhaseOnePresenter;
import com.returntrader.view.iview.IRegistrationPhaseOneView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by moorthy on 10/28/2017.
 */

public class RegistrationPhaseOnePresenter extends BasePresenter implements IRegistrationPhaseOnePresenter {


    private IRegistrationPhaseOneView iRegistrationPhaseOneView;
    private Pair<String, Long> mPair;
    private List<Country> mCountryList;
    private List<Province> mProvinceList;
    private AppConfigurationManager mAppConfigurationManager;
    private UserInfo mUserInfo;
    private FullRegistrationRequest mTempFullRegisterUserData;


    public RegistrationPhaseOnePresenter(IRegistrationPhaseOneView iView) {
        super(iView);
        this.iRegistrationPhaseOneView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        setUserDetailsData();
    }


    private void setUserDetailsData() {
        String userInfoFull = mAppConfigurationManager.getUserInfo();
        if (!(TextUtils.isEmpty(userInfoFull))) {
            mTempFullRegisterUserData = iRegistrationPhaseOneView.getCodeSnippet()
                    .getObjectFromJson(mAppConfigurationManager.getUserInfo(), FullRegistrationRequest.class);
        }

        setSpinnerAdapter();

        mUserInfo = iRegistrationPhaseOneView.getCodeSnippet().getObjectFromJson(mAppConfigurationManager.getPartialUserInfo(), UserInfo.class);

        if (mUserInfo != null) {
            if (mUserInfo.getPhoneNumber() != null) {
                String phoneNumber = mUserInfo.getPhoneNumber().getPhoneNumber().substring(1);
                iRegistrationPhaseOneView.setPhoneNumber(phoneNumber);
            }
            if (!(TextUtils.isEmpty(mUserInfo.getRsaIdentificationId()))) {
                getDateOfBirth(mUserInfo.getRsaIdentificationId());
            }
        }

        // keep this here don't move to upwards
        if (mTempFullRegisterUserData != null) {
            setDateOfBirth(mTempFullRegisterUserData.getDateOfBirth(), 0);
            iRegistrationPhaseOneView.populateUserInfo(mTempFullRegisterUserData);
        }


    }

    private void getDateOfBirth(String rsaIdentificationId) {
        String dateOfBirthFormat = rsaIdentificationId.substring(0, 7);
        String dateString = iRegistrationPhaseOneView.getCodeSnippet().getDateFromRsaId(dateOfBirthFormat);
        iRegistrationPhaseOneView.setBirthDate(dateString);
        setDateOfBirth(dateString, 0);
    }

    private int getGender(String rsaIdentificationId) {
        String genderPredication = rsaIdentificationId.substring(6, 10);
        Log.d(TAG, "genderPredication : " + genderPredication);
        int gender = Integer.parseInt(genderPredication);
        if (gender >= 0 && gender <= 4999) {
            return 0; //  female
        } else {
            return 1; // male
        }
    }


    private void setSpinnerAdapter() {
        ConfigData configData = getConfigData();
        setInitialData();
        if (configData == null) {
            return;
        }


        if (configData.getCountries() != null) {
            mCountryList = configData.getCountries();
            List<String> countryName = Stream.of(mCountryList)
                    .map(Country::getCountryName)
                    .collect(Collectors.toList());
            mCountryList.set(0, new Country("0", "0", getDefaultSpinner(Constants.SpinnerType.TYPE_CITY_OF_BIRTH), ""));
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(iRegistrationPhaseOneView.getActivity(), android.R.layout.simple_spinner_dropdown_item, countryName); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            iRegistrationPhaseOneView.setSpinnerAdapter(spinnerArrayAdapter, Constants.SpinnerType.TYPE_COUNTRY_OF_RESIDENCE);
            iRegistrationPhaseOneView.setSpinnerAdapter(spinnerArrayAdapter, Constants.SpinnerType.TYPE_NATIONALITY);
            iRegistrationPhaseOneView.setSpinnerAdapter(spinnerArrayAdapter, Constants.SpinnerType.TYPE_CITY_OF_BIRTH);
        }

        if (configData.getProvinces() != null) {
            mProvinceList = configData.getProvinces();
            mProvinceList.set(0, new Province("0", getDefaultSpinner(Constants.SpinnerType.TYPE_PROVINCE), "0"));
            List<String> provinceName = Stream.of(mProvinceList)
                    .map(Province::getProvinceName)
                    .collect(Collectors.toList());
            ArrayAdapter<String> spinnerArrayAdapterProvince = new ArrayAdapter<String>(iRegistrationPhaseOneView.getActivity(), android.R.layout.simple_spinner_dropdown_item, provinceName); //selected item will look like a spinner set from XML
            spinnerArrayAdapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            iRegistrationPhaseOneView.setSpinnerAdapter(spinnerArrayAdapterProvince, Constants.SpinnerType.TYPE_PROVINCE);
        }
    }

    private void setInitialData() {
        mCountryList = new ArrayList<>();
        mProvinceList = new ArrayList<>();
    }


    private String getDefaultSpinner(int type) {
        switch (type) {
            case Constants.SpinnerType.TYPE_PROVINCE:
                return "Choose Province";
            case Constants.SpinnerType.TYPE_RACE:
                return "Choose Race";
            default:
                return "Choose Country";
        }
    }


    private ConfigData getConfigData() {
        ConfigData configData = iRegistrationPhaseOneView.getCodeSnippet().getConfigData(mAppConfigurationManager.isConfigurationDataUpdated(),
                mAppConfigurationManager.getConfigData());
        return configData;
    }

    @Override
    public void setDateOfBirth(String date, long dateInMilliSeconds) {
        this.mPair = Pair.create(date, dateInMilliSeconds);
    }

    @Override
    public boolean isDateSelected() {
        return mPair != null;
    }

    @Override
    public void onClickSubmit(String race, String cityBirth, int nationality, int residence, int province, boolean isSaveEnabled) {
        storeUserData(race, cityBirth, nationality, residence, province, isSaveEnabled);
    }

    /***/
    private void storeUserData(String race, String cityBirth, int nationality, int residence, int province, boolean isSaveEnabled) {
        FullRegistrationRequest fullRegistrationRequest = new FullRegistrationRequest();
        if (mTempFullRegisterUserData != null) {
            fullRegistrationRequest = mTempFullRegisterUserData;
        }

        PhoneNumber phoneNumber = new PhoneNumber();

        if (mUserInfo != null) {
            if(mUserInfo.getTitle()!=null) {
                fullRegistrationRequest.setTitleId(mUserInfo.getTitle().getTitleId());
            }
            fullRegistrationRequest.setEmail(mUserInfo.getEmailId());
            fullRegistrationRequest.setGenderId(getGender(mUserInfo.getRsaIdentificationId()));
            fullRegistrationRequest.setIdentificationNumber(mUserInfo.getRsaIdentificationId());
            fullRegistrationRequest.setFirstName(mUserInfo.getFirstName());
            fullRegistrationRequest.setLastName(mUserInfo.getSurname());
            phoneNumber.setPhoneNumber(mUserInfo.getPhoneNumber().getPhoneNumber());
            fullRegistrationRequest.setSecurityQuestionAnswer(mUserInfo.getRsaIdentificationId());
        }

        phoneNumber.setCountryId(Constants.Common.DEFAULT_COUNTRY_ID);
        phoneNumber.setCountryCode(Constants.Common.DEFAULT_COUNTRY_CODE);
        phoneNumber.setPhoneNoTypeId(1);
        fullRegistrationRequest.setPhoneNumber(phoneNumber);
        BirthPlace birthPlace = new BirthPlace();
        //birthPlace.setCountryId(mCountryList.get(cityBirth).getCountryId());
        birthPlace.setCountryId(Constants.Common.DEFAULT_COUNTRY_ID);
        birthPlace.setCityName(cityBirth);
        fullRegistrationRequest.setBirthPlace(birthPlace);
        fullRegistrationRequest.setDateOfBirth(mPair.first);
        fullRegistrationRequest.setNationalityCountryId(mCountryList.get(nationality).getCountryId());
        fullRegistrationRequest.setCountryOfResidenceId(mCountryList.get(residence).getCountryId());
        fullRegistrationRequest.setSecurityQuestionId(1);

        //Extra data.
        Log.d(TAG, "race : " + race);
        Log.d(TAG, "getProvinceId : " + mProvinceList.get(province).getProvinceId());
        fullRegistrationRequest.setRace(race);
        fullRegistrationRequest.setProvince(mProvinceList.get(province).getProvinceId());
        if (isSaveEnabled) {
            saveUserData(fullRegistrationRequest);
            iRegistrationPhaseOneView.getActivity().finish();
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.BundleKey.BUNDLE_REGISTER_REQUEST, fullRegistrationRequest);
            iRegistrationPhaseOneView.navigateToPhaseTwo(bundle);
        }
    }

    private void saveUserData(FullRegistrationRequest fullRegistrationRequest) {
        String userData = iRegistrationPhaseOneView.getCodeSnippet()
                .getJsonStringFromObject(fullRegistrationRequest, FullRegistrationRequest.class);
        if (!(TextUtils.isEmpty(userData))) {
            mAppConfigurationManager.setUserInfo(userData);
        }
    }

    @Override
    public Calendar getBirthDateCalender() {

        if (mPair == null) {
            return null;
        }

        if (TextUtils.isEmpty(mPair.first)) {
            return null;
        }
        return iRegistrationPhaseOneView.getCodeSnippet().getCalendarToStandard(mPair.first);
    }

    @Override
    public String getSelectedResidence() {
        if (mTempFullRegisterUserData == null ||
                mCountryList == null || mCountryList.size() <= 0) {
            return Constants.Common.DEFAULT_COUNTRY;
        }

        return Stream.of(mCountryList).
                filter(country -> country.getCountryId()
                        .equalsIgnoreCase(mTempFullRegisterUserData.getCountryOfResidenceId()))
                .single().getCountryName();
    }

    @Override
    public String getSelectedRace() {
        if (mTempFullRegisterUserData != null) {
            return mTempFullRegisterUserData.getRace();
        }
        return getDefaultSpinner(Constants.SpinnerType.TYPE_RACE);
    }

    @Override
    public String getSelectedProvince() {
        if (mTempFullRegisterUserData != null) {
            return Stream.of(mProvinceList).
                    filter(province -> province.getProvinceId()
                            .equalsIgnoreCase(mTempFullRegisterUserData.getProvince()))
                    .single().getProvinceName();
        }
        return getDefaultSpinner(Constants.SpinnerType.TYPE_PROVINCE);
    }

    @Override
    public String getSelectedNationality() {
        if (mTempFullRegisterUserData == null ||
                mCountryList == null || mCountryList.size() <= 0) {
            return Constants.Common.DEFAULT_COUNTRY;
        }
        return Stream.of(mCountryList).
                filter(country -> country.getCountryId()
                        .equalsIgnoreCase(mTempFullRegisterUserData.getNationalityCountryId()))
                .single().getCountryName();
    }

    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCodes.NAVIGATE_REGISTRATION_PHASE_TWO:
                if (resultCode == Activity.RESULT_OK) {
                    iRegistrationPhaseOneView.getActivity().setResult(Activity.RESULT_OK);
                    iRegistrationPhaseOneView.getActivity().finish();
                } else {
                    if (data != null) {
                        FullRegistrationRequest fullRegistrationRequest = data.getParcelableExtra(Constants.BundleKey.BUNDLE_REGISTER_REQUEST);
                        if (fullRegistrationRequest != null) {
                            mTempFullRegisterUserData = fullRegistrationRequest;
                        }
                    }
                }
                break;
        }
    }


}
