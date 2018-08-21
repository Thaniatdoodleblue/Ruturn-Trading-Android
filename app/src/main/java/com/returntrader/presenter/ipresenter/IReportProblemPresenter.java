package com.returntrader.presenter.ipresenter;

import com.returntrader.model.dto.request.ReportProblemRequest;

/**
 * Created by moorthy on 11/17/2017.
 */

public interface IReportProblemPresenter extends IPresenter {

    void onClickSubmit(ReportProblemRequest reportProblemRequest);

    void navigateToViewReports();
}
