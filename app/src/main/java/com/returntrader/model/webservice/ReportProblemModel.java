package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.request.ReportProblemRequest;
import com.returntrader.model.dto.response.ReportProblemResponse;
import com.returntrader.model.listener.IBaseModelListener;

import static com.returntrader.common.Constants.Settings.REPORT_PROBLEMS;
import static com.returntrader.common.Constants.Settings.VIEW_REPORTS;

/**
 * Created by moorthy on 7/21/2017.
 */


public class ReportProblemModel extends BaseRetroFitModel<ReportProblemResponse> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<ReportProblemResponse> iBaseModelListener;


    public ReportProblemModel(IBaseModelListener<ReportProblemResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void postProblem(ReportProblemRequest reportProblemRequest) {
        enQueueTask(REPORT_PROBLEMS, ApiClient.getClient().create(ApiInterface.class).reportProblem(reportProblemRequest));
    }

    public void getReports(BaseRequest reportProblemRequest) {
        enQueueTask(VIEW_REPORTS, ApiClient.getClient().create(ApiInterface.class).getReports(reportProblemRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, ReportProblemResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
