package com.returntrader.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.returntrader.common.Constants;
import com.returntrader.helper.GraphManger;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.library.Log;
import com.returntrader.model.common.DayHistoryDo;
import com.returntrader.model.common.LineGraphDataSet;
import com.returntrader.presenter.ipresenter.IGraphFragPresenter;
import com.returntrader.view.iview.IGraphView;


public class GraphPresenter extends BasePresenter implements IGraphFragPresenter {

    private IGraphView iGraphView;
    private CompanyItemInfo mSearchDataItem;
    private GraphManger mGraphManger;
    boolean isCompanyAvailable;

    public GraphPresenter(IGraphView iView) {
        super(iView);
        this.iGraphView = iView;
        this.mGraphManger = new GraphManger(iView.getActivity(), onGraphLoadedListener);
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            mSearchDataItem = bundle.getParcelable(Constants.BundleKey.BUNDLE_COMPANY_ITEM);
            if (mSearchDataItem != null) {
                if (!TextUtils.isEmpty(mSearchDataItem.getCompanyAvailabilityStatus())) {
                    isCompanyAvailable = mSearchDataItem.getCompanyAvailabilityStatus().equalsIgnoreCase(Constants.Common.ENABLED_COMPANIES_STATUS) ? false : true;
                }
            }
            mGraphManger.isCompanyAvailable(isCompanyAvailable);
        }
    }

    /***/
    private GraphManger.OnGraphLoadedListener onGraphLoadedListener = new GraphManger.OnGraphLoadedListener() {
        @Override
        public void loadGraphView(LineGraphDataSet lineDataSet) {
            iGraphView.setGraphView(lineDataSet);
        }

        @Override
        public void showGraphLoadingProgressBar() {
            iGraphView.showProgressbar();
        }

        @Override
        public void dismissGraphLoadingProgressBar() {
            iGraphView.dismissProgressbar();
        }

        @Override
        public void showNetworkMessage() {
            iGraphView.showNetworkMessage();
        }

        @Override
        public void setHighLowAmount(Float open, Float min, Float max) {
//            Log.e("Open = " + open, "Min = " + min + " & Max = " + max);
            iGraphView.setMinMaxValue(open, min, max);
        }
    };


    private int type;
    @Override
    public void onLoad(int type) {
        this.type = type;
        mGraphManger.onLoad(type);
    }

    @Override
    public void callGraphApi() {
        if (mSearchDataItem != null) {
            mGraphManger.fetchGraphDetails(mSearchDataItem);
        }
    }

}
