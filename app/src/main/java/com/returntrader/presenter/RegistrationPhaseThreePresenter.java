package com.returntrader.presenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.ConfigData;
import com.returntrader.model.common.IncomeBand;
import com.returntrader.model.common.MaritalStatus;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.model.dto.request.Income;
import com.returntrader.model.dto.request.UpdatePasscodeRequest;
import com.returntrader.model.dto.response.FullRegisterResponse;
import com.returntrader.model.dto.response.UpdatePasscodeResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.FullRegistrationModel;
import com.returntrader.model.webservice.UpdatePassCodeModel;
import com.returntrader.presenter.ipresenter.IRegistrationPhaseThreePresenter;
import com.returntrader.view.iview.IRegistrationPhaseThreeView;

import java.util.ArrayList;
import java.util.List;

import static com.returntrader.common.Constants.RequestCodes.TASKID_DOFULLREGISTRATION;

/**
 * Created by moorthy on 10/28/2017.
 */

public class RegistrationPhaseThreePresenter extends BasePresenter implements IRegistrationPhaseThreePresenter {
    private IRegistrationPhaseThreeView iRegistrationPhaseThreeView;
    private FullRegistrationRequest mFullRegistrationRequest;
    private FullRegistrationModel mFullRegistrationModel;
    private UpdatePassCodeModel mUpdatePassCodeModel;
    private AppConfigurationManager mAppConfigurationManager;
    List<IncomeBand> mIncomeBands;
    List<MaritalStatus> mMaritalStatus;

    public RegistrationPhaseThreePresenter(IRegistrationPhaseThreeView iView) {
        super(iView);
        this.iRegistrationPhaseThreeView = iView;
        this.mFullRegistrationModel = new FullRegistrationModel(fullRegisterResponseIBaseModelListener);
        mUpdatePassCodeModel = new UpdatePassCodeModel(updatePasscodeResponseIBaseModelListener);
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            mFullRegistrationRequest = bundle.getParcelable(Constants.BundleKey.BUNDLE_REGISTER_REQUEST);
            iRegistrationPhaseThreeView.populateUserInformation(mFullRegistrationRequest);
        }
        setSpinnerAdapter();
    }


    /***/
    private void setSpinnerAdapter() {
        initializeArray();
        ConfigData configData = iRegistrationPhaseThreeView.getCodeSnippet().getConfigData(mAppConfigurationManager.isConfigurationDataUpdated(), mAppConfigurationManager.getConfigData());
        if (configData != null) {
            if (configData.getIncomeBands() != null) {
                mIncomeBands = configData.getIncomeBands();
                List<String> countryName = Stream.of(mIncomeBands)
                        .map(IncomeBand::getName)
                        .collect(Collectors.toList());
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(iRegistrationPhaseThreeView.getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, countryName); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                iRegistrationPhaseThreeView.setSpinnerAdapter(spinnerArrayAdapter, Constants.SpinnerType.TYPE_INCOME);

            }

            if (configData.getMaritalStatuses() != null) {
                mMaritalStatus = configData.getMaritalStatuses();
                List<String> countryName = Stream.of(mMaritalStatus)
                        .map(MaritalStatus::getName)
                        .collect(Collectors.toList());
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(iRegistrationPhaseThreeView.getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, countryName); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                iRegistrationPhaseThreeView.setSpinnerAdapter(spinnerArrayAdapter, Constants.SpinnerType.TYPE_MARITAL_STATUS);

            }


        }
    }

    /***/
    private void initializeArray() {
        mIncomeBands = new ArrayList<>();
        mMaritalStatus = new ArrayList<>();
    }


    /***/
    private String getPassword(String userName) {
        return iRegistrationPhaseThreeView.getActivity().getString(R.string.generate_password, userName) + System.currentTimeMillis();
    }

    /***/
    private String getUserName(String userName) {
        return userName + String.valueOf(System.currentTimeMillis());
    }

    @Override
    public void onClickSubmit(String taxNumber, int incomePosition, int maritalStatus, boolean isSaveEnabled) {
        if (mFullRegistrationRequest != null) {
            //mFullRegistrationRequest.setTitleId("1");
            mFullRegistrationRequest.setTaxNumber(taxNumber);
            mFullRegistrationRequest.setUsername(getUserName(mFullRegistrationRequest.getFirstName() + "" + mFullRegistrationRequest.getLastName()));
            String password = getPassword(mFullRegistrationRequest.getFirstName() + "" + mFullRegistrationRequest.getLastName());
            mFullRegistrationRequest.setPassword(password);
            mFullRegistrationRequest.setConfirmPassword(password);
            Income income = new Income();
            income.setIncomeBandId(mIncomeBands.get(incomePosition).getIncomeBandId());
            income.setCurrentEarningsStatus(1);
            mFullRegistrationRequest.setIncomeBand(income);
            mFullRegistrationRequest.setMaritalStatusId(mMaritalStatus.get(maritalStatus).getMaritalStatusId());
            if (isSaveEnabled) {
                String userInfo = iRegistrationPhaseThreeView.getCodeSnippet()
                        .getJsonStringFromObject(mFullRegistrationRequest, FullRegistrationRequest.class);
                mAppConfigurationManager.setUserInfo(userInfo);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.BundleKey.BUNDLE_REGISTER_REQUEST, mFullRegistrationRequest);
                iRegistrationPhaseThreeView.postResult(bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.BundleKey.BUNDLE_REGISTER_REQUEST, mFullRegistrationRequest);
                if (iRegistrationPhaseThreeView.getCodeSnippet().hasNetworkConnection()) {
                    iRegistrationPhaseThreeView.showProgressbar();
                    mFullRegistrationModel.doFullRegistration(mFullRegistrationRequest);
                } else {
                    iRegistrationPhaseThreeView.showNetworkMessage();
                }
            }

        } else {
            iRegistrationPhaseThreeView.showMessage("Problem with full registration");
        }
    }


    @Override
    public String getSelectedIncome() {
        if (mFullRegistrationRequest == null ||
                mIncomeBands == null || mIncomeBands.size() <= 0 || mFullRegistrationRequest.getIncomeBand() == null) {
            return "0 – 189 880";
        }

        return Stream.of(mIncomeBands).
                filter(country -> country.getIncomeBandId()
                        .equalsIgnoreCase(mFullRegistrationRequest.getIncomeBand().getIncomeBandId()))
                .single().getName();
    }

    @Override
    public String getSelectedMaritalStatus() {
        if (mFullRegistrationRequest == null || TextUtils.isEmpty(mFullRegistrationRequest.getMaritalStatusId())
                || mMaritalStatus == null || mMaritalStatus.size() <= 0) {
            return "Single";
        }

        return Stream.of(mMaritalStatus).
                filter(country -> country.getMaritalStatusId()
                        .equalsIgnoreCase(mFullRegistrationRequest.getMaritalStatusId()))
                .single().getName();
    }

    /***/
    private IBaseModelListener<UpdatePasscodeResponse> updatePasscodeResponseIBaseModelListener = new IBaseModelListener<UpdatePasscodeResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, UpdatePasscodeResponse response) {
            //Do Nothing
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            //Do Nothing
        }
    };

    /***/
    private IBaseModelListener<FullRegisterResponse> fullRegisterResponseIBaseModelListener = new IBaseModelListener<FullRegisterResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, FullRegisterResponse response) {
            if (response != null) {
                mAppConfigurationManager.setUserId(response.getUserId());
                iRegistrationPhaseThreeView.showMessage("You have successfully registered");//response.getMessage()
                if (mFullRegistrationRequest != null) {
                    String userInfo = iRegistrationPhaseThreeView.getCodeSnippet().getJsonStringFromObject(mFullRegistrationRequest, FullRegistrationRequest.class);
                    mAppConfigurationManager.setUserInfo(userInfo);
                }
                iRegistrationPhaseThreeView.registrationSuccessful();
                updatePIN(response.getUserId());
            }
            iRegistrationPhaseThreeView.dismissProgressbar();
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iRegistrationPhaseThreeView.dismissProgressbar();
            iRegistrationPhaseThreeView.showMessage("Given details are incorrect. please check.");
        }
    };

    /***/
    private void updatePIN(String userId) {
        if (iRegistrationPhaseThreeView.getCodeSnippet().hasNetworkConnection()) {
            UpdatePasscodeRequest request = new UpdatePasscodeRequest();
            request.setUser_id(userId);
            request.setPasscode(mAppConfigurationManager.getAuthenticationPin().replace("\n", ""));
            request.setPassKey("0");//intimate to check with UserId.
            mUpdatePassCodeModel.dpUpdatePin(request);
        } else {
            iRegistrationPhaseThreeView.showNetworkMessage();
        }
    }
}
