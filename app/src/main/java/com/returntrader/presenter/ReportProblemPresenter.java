package com.returntrader.presenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.UserInfo;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.request.ReportProblemRequest;
import com.returntrader.model.dto.response.ReportProblemResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.ReportProblemModel;
import com.returntrader.presenter.ipresenter.IReportProblemPresenter;
import com.returntrader.view.iview.IReportProblemView;

import static com.returntrader.common.Constants.BundleKey.BUNDLE_VIEW_REPORTS;
import static com.returntrader.common.Constants.Settings.REPORT_PROBLEMS;

/**
 * Created by moorthy on 11/17/2017.
 */

public class ReportProblemPresenter extends BasePresenter implements IReportProblemPresenter {


    private IReportProblemView iReportProblemView;
    private ReportProblemModel mReportProblemModel;
    private AppConfigurationManager mAppConfigurationManager;
    private ReportProblemResponse mReportProblemResponse;

    public ReportProblemPresenter(IReportProblemView iView) {
        super(iView);
        this.iReportProblemView = iView;
        this.mReportProblemModel = new ReportProblemModel(responseIBaseModelListener);
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (TextUtils.isEmpty(mAppConfigurationManager.getUserId())) {
            iReportProblemView.canViewReport(View.GONE);
            iReportProblemView.canViewUpdates(View.GONE);
        } else {
            iReportProblemView.canViewUpdates(View.VISIBLE);
            getListOfReports();
        }
    }

    /***/
    private void getListOfReports() {
        if (iReportProblemView.getCodeSnippet().hasNetworkConnection()) {
            mReportProblemModel.getReports(new BaseRequest(mAppConfigurationManager.getUserId()));
        } else {
            iReportProblemView.showNetworkMessage();
        }
    }

    /***/
    private UserInfo getUserInfo() {
        UserInfo userInfo = iReportProblemView.getCodeSnippet().getObjectFromJson(mAppConfigurationManager.getPartialUserInfo(), UserInfo.class);
        if (userInfo != null) {
            return userInfo;
        }
        return null;
    }


    @Override
    public void onClickSubmit(ReportProblemRequest reportProblemRequest) {
        if (iReportProblemView.getCodeSnippet().hasNetworkConnection()) {
            if (TextUtils.isEmpty(mAppConfigurationManager.getUserId())) {
                if (getUserInfo() != null) {
                    reportProblemRequest.setEmailId(getUserInfo().getEmailId());
                }
            } else {
                reportProblemRequest.setUserId(mAppConfigurationManager.getUserId());
            }
            iReportProblemView.showProgressbar();
            mReportProblemModel.postProblem(reportProblemRequest);
        } else {
            iReportProblemView.showNetworkMessage();
        }
    }


    @Override
    public void navigateToViewReports() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_VIEW_REPORTS, mReportProblemResponse);
        iReportProblemView.navigateToViewReports(bundle);
    }


    /***/
    private IBaseModelListener<ReportProblemResponse> responseIBaseModelListener = new IBaseModelListener<ReportProblemResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, ReportProblemResponse response) {
            iReportProblemView.dismissProgressbar();

            if (taskId == REPORT_PROBLEMS) {
                iReportProblemView.showMessage("Your report has been sent successfully. We will contact you very soon.");
                iReportProblemView.clearText();
                iReportProblemView.getActivity().finish();
            } else {
                if (response != null) {
                    if (response.getReports().size() > 0) {
                        mReportProblemResponse = response;
                        iReportProblemView.canViewReport(View.VISIBLE);
                    } else {
                        iReportProblemView.canViewReport(View.GONE);
                    }
                }
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iReportProblemView.dismissProgressbar();
        }
    };
}
