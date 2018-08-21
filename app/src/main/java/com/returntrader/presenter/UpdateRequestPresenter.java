package com.returntrader.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.returntrader.R;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.ProfileUpdateRequest;
import com.returntrader.model.dto.request.ReportProblemRequest;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.dto.response.ReportProblemResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.ReportProblemModel;
import com.returntrader.model.webservice.UpdatePreferenceModel;
import com.returntrader.presenter.ipresenter.IUpdateRequestPresenter;
import com.returntrader.view.iview.IReportProblemView;
import com.returntrader.view.iview.IUpdateRequestView;
import com.returntrader.view.iview.IView;

/**
 * Created by moorthy on 12/12/2017.
 */

public class UpdateRequestPresenter extends BasePresenter implements IUpdateRequestPresenter {


    private UpdatePreferenceModel mUpdatePreferenceModel;
    private AppConfigurationManager mAppConfigurationManager;

    private IUpdateRequestView iUpdateRequestView;

    public UpdateRequestPresenter(IUpdateRequestView iView) {
        super(iView);
        this.iUpdateRequestView = iView;
        this.mUpdatePreferenceModel = new UpdatePreferenceModel(responseIBaseModelListener);
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {

    }

    @Override
    public void onClickSubmit(String content) {
        submitUpdateRequest(content);
    }


    private void submitUpdateRequest(String content) {
        if (iUpdateRequestView.getCodeSnippet().hasNetworkConnection()) {
            ProfileUpdateRequest reportProblemRequest = new ProfileUpdateRequest();
            reportProblemRequest.setUserId(mAppConfigurationManager.getUserId());
            reportProblemRequest.setContent(content);
            iUpdateRequestView.showProgressbar();
            mUpdatePreferenceModel.sendProfileEditRequest(reportProblemRequest);
        } else {
            iUpdateRequestView.showNetworkMessage();
        }
    }

    private IBaseModelListener<BaseResponse> responseIBaseModelListener = new IBaseModelListener<BaseResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, BaseResponse response) {
            iUpdateRequestView.dismissProgressbar();
            iUpdateRequestView.showMessage(R.string.txt_msg_success_profile_update);
            iUpdateRequestView.getActivity().finish();
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iUpdateRequestView.dismissProgressbar();
        }
    };
}
