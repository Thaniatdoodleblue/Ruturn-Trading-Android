package com.returntrader.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.helper.LoginHelper;
import com.returntrader.library.CustomException;
import com.returntrader.library.Log;
import com.returntrader.model.common.BirthPlace;
import com.returntrader.model.common.ConfigData;
import com.returntrader.model.common.Country;
import com.returntrader.model.common.IncomeBand;
import com.returntrader.model.common.MaritalStatus;
import com.returntrader.model.common.Province;
import com.returntrader.model.dto.request.EditProfileRequest;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.model.dto.request.Income;
import com.returntrader.model.dto.response.LoginResponse;
import com.returntrader.model.dto.response.LoginUserInfo;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.LoginModel;
import com.returntrader.presenter.ipresenter.IEditProfileProfilePhaseTwoPresenter;
import com.returntrader.view.iview.IEditProfilePhaseTwoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moorthy on 12/8/2017.
 */

public class EditProfileProfilePhaseTwoPresenter extends BasePresenter implements IEditProfileProfilePhaseTwoPresenter {


    private AppConfigurationManager mAppConfigurationManager;
    private FullRegistrationRequest mUserInfo;
    private IEditProfilePhaseTwoView iEditProfilePhaseOneView;
    private List<Country> mCountryList;
    private List<Province> mProvinceList;
    private EditProfileRequest mEditProfileRequest;
    private List<IncomeBand> mIncomeBands;
    private List<MaritalStatus> mMaritalStatus;
    private LoginModel mEdiProfileModel;
    private LoginHelper mLoginHelper;

    public EditProfileProfilePhaseTwoPresenter(IEditProfilePhaseTwoView iView) {
        super(iView);
        this.iEditProfilePhaseOneView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        this.mEdiProfileModel = new LoginModel(editResponseIBaseModelListener);
        this.mLoginHelper = new LoginHelper(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            mEditProfileRequest = bundle.getParcelable(Constants.BundleKey.BUNDLE_EDIT_REQUEST);
        }
        String userInfoFull = mAppConfigurationManager.getUserInfo();
        if (!(TextUtils.isEmpty(userInfoFull))) {
            mUserInfo = iEditProfilePhaseOneView.getCodeSnippet()
                    .getObjectFromJson(mAppConfigurationManager.getUserInfo(), FullRegistrationRequest.class);
            if (mUserInfo != null) {
                iEditProfilePhaseOneView.setUserData(mUserInfo);
            }
        }

        setSpinnerAdapter();

    }


    private IBaseModelListener<LoginResponse> editResponseIBaseModelListener = new IBaseModelListener<LoginResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, LoginResponse response) {
            saveUserInfo(response.getLoginUserInfo());

        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iEditProfilePhaseOneView.dismissProgressbar();
        }
    };

    private void saveUserInfo(LoginUserInfo loginUserInfo) {
        boolean isSyncDone = mLoginHelper.syncUserInfo(loginUserInfo, LoginHelper.SyncFrom.FROM_EDIT_PROFILE);
        if (isSyncDone) {
            iEditProfilePhaseOneView.dismissProgressbar();
            iEditProfilePhaseOneView.showMessage("Your profile is successfully updated.");
            iEditProfilePhaseOneView.setUpdateResult(Activity.RESULT_OK);
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
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(iEditProfilePhaseOneView.getActivity(), android.R.layout.simple_spinner_dropdown_item, countryName); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            iEditProfilePhaseOneView.setSpinnerAdapter(spinnerArrayAdapter, Constants.SpinnerType.TYPE_COUNTRY_OF_RESIDENCE);
            iEditProfilePhaseOneView.setSpinnerAdapter(spinnerArrayAdapter, Constants.SpinnerType.TYPE_NATIONALITY);
            iEditProfilePhaseOneView.setSpinnerAdapter(spinnerArrayAdapter, Constants.SpinnerType.TYPE_CITY_OF_BIRTH);
        }

        if (configData.getProvinces() != null) {
            mProvinceList = configData.getProvinces();
            mProvinceList.set(0, new Province("0", getDefaultSpinner(Constants.SpinnerType.TYPE_PROVINCE), "0"));
            List<String> provinceName = Stream.of(mProvinceList)
                    .map(Province::getProvinceName)
                    .collect(Collectors.toList());
            ArrayAdapter<String> spinnerArrayAdapterProvince = new ArrayAdapter<String>(iEditProfilePhaseOneView.getActivity(), android.R.layout.simple_spinner_dropdown_item, provinceName); //selected item will look like a spinner set from XML
            spinnerArrayAdapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            iEditProfilePhaseOneView.setSpinnerAdapter(spinnerArrayAdapterProvince, Constants.SpinnerType.TYPE_PROVINCE);
        }


        if (configData.getIncomeBands() != null) {
            mIncomeBands = configData.getIncomeBands();
            List<String> countryName = Stream.of(mIncomeBands)
                    .map(IncomeBand::getName)
                    .collect(Collectors.toList());
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(iEditProfilePhaseOneView.getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, countryName); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            iEditProfilePhaseOneView.setSpinnerAdapter(spinnerArrayAdapter, Constants.SpinnerType.TYPE_INCOME);

        }

        if (configData.getMaritalStatuses() != null) {
            mMaritalStatus = configData.getMaritalStatuses();
            List<String> countryName = Stream.of(mMaritalStatus)
                    .map(MaritalStatus::getName)
                    .collect(Collectors.toList());
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(iEditProfilePhaseOneView.getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, countryName); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            iEditProfilePhaseOneView.setSpinnerAdapter(spinnerArrayAdapter, Constants.SpinnerType.TYPE_MARITAL_STATUS);

        }
    }

    private void setInitialData() {
        mCountryList = new ArrayList<>();
        mProvinceList = new ArrayList<>();
        mIncomeBands = new ArrayList<>();
        mMaritalStatus = new ArrayList<>();
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
        ConfigData configData = iEditProfilePhaseOneView.getCodeSnippet().getConfigData(mAppConfigurationManager.isConfigurationDataUpdated(),
                mAppConfigurationManager.getConfigData());
        return configData;
    }

    @Override
    public String getSelectedResidence() {
        if (mUserInfo == null ||
                mCountryList == null || mCountryList.size() <= 0) {
            return Constants.Common.DEFAULT_COUNTRY;
        }

        return Stream.of(mCountryList).
                filter(country -> country.getCountryId()
                        .equalsIgnoreCase(mUserInfo.getCountryOfResidenceId()))
                .single().getCountryName();
    }


    @Override
    public void onClickSubmit(int province, String postalCode, int residence, String cityBirth, int nationality, String taxNumber, int incomePosition, int maritalStatus) {
        mEditProfileRequest.setUserId(mAppConfigurationManager.getUserId());
        mEditProfileRequest.setTaxNumber(taxNumber);
        mEditProfileRequest.setPostalCode(postalCode);
        mEditProfileRequest.setProvince(mProvinceList.get(province).getProvinceId());
        BirthPlace birthPlace = new BirthPlace();
        birthPlace.setCountryId(Constants.Common.DEFAULT_COUNTRY_ID);
        birthPlace.setCityName(cityBirth);
        mEditProfileRequest.setBirthPlace(birthPlace);
        mEditProfileRequest.setNationalityCountryId(mCountryList.get(nationality).getCountryId());
        mEditProfileRequest.setCountryOfResidenceId(mCountryList.get(residence).getCountryId());
        Income income = new Income();
        income.setIncomeBandId(mIncomeBands.get(incomePosition).getIncomeBandId());
        income.setCurrentEarningsStatus(1);
        mEditProfileRequest.setIncome(income);
        mEditProfileRequest.setMaritalStatusId(mMaritalStatus.get(maritalStatus).getMaritalStatusId());
        String editProfileRequest = iEditProfilePhaseOneView.getCodeSnippet().getJsonStringFromObject(mEditProfileRequest, EditProfileRequest.class);
        Log.d(TAG, "editProfileRequest : " + editProfileRequest);
        if (iEditProfilePhaseOneView.getCodeSnippet().hasNetworkConnection()) {
            iEditProfilePhaseOneView.showProgressbar();
            mEdiProfileModel.doEditProfile(mEditProfileRequest);
        } else {
            iEditProfilePhaseOneView.showNetworkMessage();
        }
    }

    @Override
    public String getSelectedIncome() {

       // Log.d(TAG,"mUserInfo.getIncomeBand().getIncomeBandId() :"  +mUserInfo.getIncomeBand().getIncomeBandId());

        if (mUserInfo == null ||
                mIncomeBands == null || mIncomeBands.size() <= 0 || mUserInfo.getIncomeBand() == null) {
            return "0 – 189 880";
        }

        return Stream.of(mIncomeBands).
                filter(country -> country.getIncomeBandId()
                        .equalsIgnoreCase(mUserInfo.getIncomeBand().getIncomeBandId()))
                .single().getName();
    }

    @Override
    public String getSelectedMaritalStatus() {
        if (mUserInfo == null || TextUtils.isEmpty(mUserInfo.getMaritalStatusId())
                || mMaritalStatus == null || mMaritalStatus.size() <= 0) {
            return "Single";
        }

        return Stream.of(mMaritalStatus).
                filter(country -> country.getMaritalStatusId()
                        .equalsIgnoreCase(mUserInfo.getMaritalStatusId()))
                .single().getName();
    }

    @Override
    public String getSelectedProvince() {
        if (mUserInfo != null) {
            return Stream.of(mProvinceList).
                    filter(province -> province.getProvinceId()
                            .equalsIgnoreCase(mUserInfo.getProvince()))
                    .single().getProvinceName();
        }
        return getDefaultSpinner(Constants.SpinnerType.TYPE_PROVINCE);
    }

    @Override
    public String getSelectedNationality() {
        if (mUserInfo == null ||
                mCountryList == null || mCountryList.size() <= 0) {
            return Constants.Common.DEFAULT_COUNTRY;
        }
        return Stream.of(mCountryList).
                filter(country -> country.getCountryId()
                        .equalsIgnoreCase(mUserInfo.getNationalityCountryId()))
                .single().getCountryName();
    }
}
