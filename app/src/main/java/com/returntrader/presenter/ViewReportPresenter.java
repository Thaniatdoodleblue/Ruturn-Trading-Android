package com.returntrader.presenter;

import android.os.Bundle;

import com.returntrader.adapter.ViewReportAdapter;
import com.returntrader.model.common.Report;
import com.returntrader.model.dto.response.ReportProblemResponse;
import com.returntrader.presenter.ipresenter.IViewReportPresenter;
import com.returntrader.utils.CodeSnippet;
import com.returntrader.view.iview.IViewReportView;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.returntrader.common.Constants.BundleKey.BUNDLE_VIEW_REPORTS;

/**
 * Created by moorthy on 9/20/2017.
 */

public class ViewReportPresenter extends BasePresenter implements IViewReportPresenter {
    private IViewReportView iViewReportView;
    //    private AppConfigurationManager mAppConfigurationManager;
    private ViewReportAdapter mViewReportAdapter;
    private CodeSnippet mCodeSnippet;

    public ViewReportPresenter(IViewReportView iView) {
        super(iView);
        this.iViewReportView = iView;
        mCodeSnippet = new CodeSnippet(iView.getActivity());
//        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            ReportProblemResponse mReportProblemResponse = bundle.getParcelable(BUNDLE_VIEW_REPORTS);
            setReportAdapter(mReportProblemResponse.getReports());
        }
    }


    /***/
    private void setReportAdapter(List<Report> reportList) {
        Collections.sort(reportList, (lhs, rhs) -> {
            Date d1 = mCodeSnippet.getDateTime(lhs.getCreatedAt());
            Date d2 = mCodeSnippet.getDateTime(rhs.getCreatedAt());
            Date now = null;
            if (mCodeSnippet != null) {
                now = mCodeSnippet.getCurrentDate();
            }
            if (d1.after(now) && d2.after(now)) {
                return d1.compareTo(d2);
            }
            if (d1.before(now) && d2.before(now)) {
                return -d1.compareTo(d2);
            }
            return -d1.compareTo(d2);
        });

        if (mViewReportAdapter == null) {
            mViewReportAdapter = new ViewReportAdapter(reportList, iViewReportView.getCodeSnippet());
            iViewReportView.setReportListAdapter(mViewReportAdapter);
        } else {
            mViewReportAdapter.resetItems(reportList);
        }
    }
}
