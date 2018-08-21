package com.returntrader.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.ConfigData;
import com.returntrader.model.common.Title;
import com.returntrader.model.common.UserInfo;
import com.returntrader.model.dto.request.OtpRequest;
import com.returntrader.model.dto.response.OtpResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.OtpModel;
import com.returntrader.presenter.ipresenter.IBasicInfoPresenter;
import com.returntrader.view.iview.IBasicInfoView;

import java.util.List;

import static com.returntrader.common.Constants.OtpModelTaskId.QUEUE_ID_SUBMIT_PHONE_NUMBER;
import static com.returntrader.common.Constants.OtpModelTaskId.QUEUE_ID_VERIFY_PHONE_NUMBER;

/**
 * Created by moorthy on 8/24/2017.
 */

public class BasicInfoPresenter extends BasePresenter implements IBasicInfoPresenter {


    private IBasicInfoView iBasicInfoView;
    private List<Title> mTitleList;
    private AppConfigurationManager mAppConfigurationManager;
    private OtpModel mOtpModel;
    private UserInfo mUserInfo;

    public BasicInfoPresenter(IBasicInfoView iView) {
        super(iView);
        this.iBasicInfoView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        this.mOtpModel = new OtpModel(otpResponseListener);
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        loadConfigurationData();
    }


    /***/
    private void setTitleSpinnerAdapter(List<Title> titleList) {
        this.mTitleList = titleList;
        List<String> names = Stream.of(titleList)
                .map(Title::getTitleName)
                .collect(Collectors.toList());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(iBasicInfoView.getActivity(), android.R.layout.simple_spinner_dropdown_item, names);
        iBasicInfoView.setUpTitleSpinner(spinnerArrayAdapter);
    }

    /***/
    private void loadConfigurationData() {
        ConfigData configData = iBasicInfoView.getCodeSnippet().getConfigData(mAppConfigurationManager.isConfigurationDataUpdated(), mAppConfigurationManager.getConfigData());
        if (configData != null) {
            if (configData.getTitles() != null) {
                setTitleSpinnerAdapter(configData.getTitles());
            }
        }
    }

    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResultPresenter : " + "requestCode : " + requestCode + " resultCode : " + resultCode);
        switch (requestCode) {
            case Constants.RequestCodes.NAVIGATE_BASIC_INFO_TO_OTP_VERIFICATION:
                if (resultCode == Activity.RESULT_OK) {
                    iBasicInfoView.getActivity().finish();
                }
                break;
        }
    }

    @Override
    public void onClickSubmit(UserInfo userInfo, int selectedItemPosition) {
        if (iBasicInfoView.getCodeSnippet().hasNetworkConnection()) {
            this.selectedItemPosition = selectedItemPosition;
            this.mUserInfo = userInfo;
            iBasicInfoView.showProgressbar();
            mUserInfo.setTitle(mTitleList.get(selectedItemPosition));
            OtpRequest request = new OtpRequest();
            request.setMobileNumber(userInfo.getPhoneNumber().getPhoneNumber());
            mOtpModel.verifyPhoneNumber(request);
        } else {
            iBasicInfoView.showNetworkMessage();
        }
    }

    private int selectedItemPosition = 0;

    /***/
    private void onSubmitPhoneNumber() {
        if (iBasicInfoView.getCodeSnippet().hasNetworkConnection()) {
            if (mTitleList.size() > selectedItemPosition) {
                iBasicInfoView.showProgressbar();
                mOtpModel.submitPhoneNumber(new OtpRequest(mUserInfo.getPhoneNumber().getPhoneNumber()));
            }
        } else {
            iBasicInfoView.showNetworkMessage();
        }
    }

    /***/
    private IBaseModelListener<OtpResponse> otpResponseListener = new IBaseModelListener<OtpResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, OtpResponse response) {
            iBasicInfoView.dismissProgressbar();
            iBasicInfoView.showMessage(response.getOtp());
            if (response.isSuccess()) {
                if (taskId == QUEUE_ID_SUBMIT_PHONE_NUMBER) {
                    mAppConfigurationManager.setPartialUserInfo(iBasicInfoView.getCodeSnippet().getJsonStringFromObject(mUserInfo, UserInfo.class));
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.BundleKey.BUNDLE_OTP_VERIFY_FROM, Constants.OtpVerifyNavigation.FROM_REGISTRATION);
                    bundle.putParcelable(Constants.BundleKey.BUNDLE_USER_INFO, mUserInfo);
                    iBasicInfoView.navigateToVerifyOtp(bundle);
                } else if (taskId == QUEUE_ID_VERIFY_PHONE_NUMBER) {
                    onSubmitPhoneNumber();
                }
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iBasicInfoView.dismissProgressbar();
            if (e != null) {
                iBasicInfoView.showMessage(e.getException());
            }
        }
    };
}
