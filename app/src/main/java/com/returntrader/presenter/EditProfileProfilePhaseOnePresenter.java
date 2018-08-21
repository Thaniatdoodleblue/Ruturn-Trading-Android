package com.returntrader.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;

import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.model.dto.request.EditProfileRequest;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.presenter.ipresenter.IEditProfileProfilePhaseOnePresenter;
import com.returntrader.view.iview.IEditProfilePhaseOneView;

import java.util.Calendar;

/**
 * Created by moorthy on 12/8/2017.
 */

public class EditProfileProfilePhaseOnePresenter extends BasePresenter implements IEditProfileProfilePhaseOnePresenter {


    private AppConfigurationManager mAppConfigurationManager;
    private FullRegistrationRequest mFullRegistrationRequest;
    private IEditProfilePhaseOneView iEditProfilePhaseOneView;
    private Pair<String, Long> mPair;

    public EditProfileProfilePhaseOnePresenter(IEditProfilePhaseOneView iView) {
        super(iView);
        this.iEditProfilePhaseOneView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        String userInfoFull = mAppConfigurationManager.getUserInfo();
        if (!(TextUtils.isEmpty(userInfoFull))) {
            mFullRegistrationRequest = iEditProfilePhaseOneView.getCodeSnippet()
                    .getObjectFromJson(mAppConfigurationManager.getUserInfo(), FullRegistrationRequest.class);
            if (mFullRegistrationRequest != null) {
                iEditProfilePhaseOneView.setUserData(mFullRegistrationRequest);
                setDateOfBirth(mFullRegistrationRequest.getDateOfBirth(), 0);
            }
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
        return iEditProfilePhaseOneView.getCodeSnippet().getCalendarToStandard(mPair.first);
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
    public void onClickSubmit(EditProfileRequest editProfileRequest) {
        if (iEditProfilePhaseOneView.getCodeSnippet().hasNetworkConnection()) {
            editProfileRequest.setDateOfBirth(mPair.first);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.BundleKey.BUNDLE_EDIT_REQUEST, editProfileRequest);
            iEditProfilePhaseOneView.navigateToPhaseTwo(bundle);
        }
    }


    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.RequestCodes.NAVIGATE_EDIT_PROFILE_PHASE_TWO:
                if (resultCode == Activity.RESULT_OK){
                    iEditProfilePhaseOneView.getActivity().finish();
                }
                break;
        }

    }
}
