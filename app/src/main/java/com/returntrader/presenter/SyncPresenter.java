package com.returntrader.presenter;

import android.os.AsyncTask;
import android.os.Bundle;

import com.returntrader.common.Constants;
import com.returntrader.common.RetroFitBase;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.database.DaoSession;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.model.dto.request.SearchHomeRequest;
import com.returntrader.model.webservice.ApiClient;
import com.returntrader.model.webservice.ApiInterface;
import com.returntrader.model.webservice.executor.IGeneralResponseListener;
import com.returntrader.presenter.ipresenter.ISyncPresenter;
import com.returntrader.view.iview.ISyncView;

import java.util.List;

import retrofit2.Call;

/**
 * Created by guru on 8/19/2017
 */

public class SyncPresenter extends BasePresenter implements ISyncPresenter {


    private ISyncView iSyncView;
    private AppConfigurationManager mAppConfigurationManager;

    public SyncPresenter(ISyncView iView) {
        super(iView);
        this.iSyncView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        iSyncView.setNextButtonVisibility();
        iSyncView.getCodeSnippet().setAllDefaultTopic();
        setPartialRegisterCompleted();
        if (!(isDataDownloaded())) {
            callApi();
        }
    }

    private void callApi() {
        if (iSyncView.getCodeSnippet().hasNetworkConnection()) {
            SearchHomeRequest searchHomeRequest = new SearchHomeRequest();
            searchHomeRequest.setPage(0);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<CompanyItemInfo>> call = apiService.callInstrument(searchHomeRequest);
            RetroFitBase.getInstance().retrofitEnqueue(call, mResponseListener, Constants.LoadCompanyList.LOAD_DAY);
        } else {
            iSyncView.showNetworkMessage();
        }
    }


    /***/
    private void insertIntoDatabase(List<CompanyItemInfo> companyList) {
        DaoSession daoSession = TraderApplication.getInstance().getDaoSession();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //iSyncView.showStatusMessage("Started to saving data in database");
            }

            @Override
            protected Void doInBackground(Void... lists) {
                daoSession.getCompanyItemInfoDao().insertOrReplaceInTx(companyList);
                return null;
            }

            @Override
            protected void onPostExecute(Void success) {
                super.onPostExecute(success);
                iSyncView.startDelayPriceUpdateService();
                mAppConfigurationManager.setSyncStatus(true);
                iSyncView.setNextButtonVisibility();
            }
        }.execute();

    }


    private IGeneralResponseListener<List<CompanyItemInfo>> mResponseListener = new IGeneralResponseListener<List<CompanyItemInfo>>() {
        @Override
        public void onSuccess(List<CompanyItemInfo> companyList, int flag) {
            /*flag = 0 To getting day company list
            * flag =1 search api results
            * flag =2 To get top40 */


            if (companyList == null || companyList.size() <= 0) {
                //iSyncView.showStatusMessage("Data Not Found!,Try Again!");
                return;
            }

            //iSyncView.showStatusMessage("Data downloaded from api");
            switch (flag) {
                case Constants.LoadCompanyList.LOAD_DAY:
                    insertIntoDatabase(companyList);
                    break;

            }
        }

        @Override
        public void onFailure(String response, int flag) {
            switch (flag) {
                case Constants.LoadCompanyList.LOAD_DAY:
                    // iSyncView.showMessage("No results found for the recommended search");
                    break;
            }
        }

        @Override
        public void onFailure(Throwable throwable, int flag) {
            iSyncView.showMessage(throwable.getMessage());
            iSyncView.dismissProgressbar();
        }

        @Override
        public void showDialog(String response, int flag) {
            iSyncView.showMessage(response);
            iSyncView.dismissProgressbar();

        }

        @Override
        public void showErrorDialog(String errorResponse, int flag) {
            iSyncView.showMessage(errorResponse);
            iSyncView.dismissProgressbar();

        }

        @Override
        public void showInternalServerErrorDialog(String errorResponse, int flag) {
            iSyncView.showMessage(errorResponse);
            iSyncView.dismissProgressbar();

        }

        @Override
        public void logOut(int flag) {

        }
    };

    @Override
    public boolean isDataDownloaded() {
        //return new AppConfigurationManager(iSyncView.getActivity()).getSyncStatus();
        return true;
    }

    @Override
    public void setPartialRegisterCompleted() {
        mAppConfigurationManager.setPartialRegisterCompleted(true);
    }
}
