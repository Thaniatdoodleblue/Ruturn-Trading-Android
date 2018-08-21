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
import com.returntrader.presenter.ipresenter.IStartUpPresenter;
import com.returntrader.view.iview.IStartUpView;

import java.util.List;

import retrofit2.Call;

/**
 * Created by moorthy on 9/14/2017.
 */

public class StartUpPresenter extends BasePresenter implements IStartUpPresenter {

    private IStartUpView iStartUpView;
    private AppConfigurationManager mAppConfigurationManager;
//    private GetConfigurationDataModel mGetConfigurationDataModel;

    public StartUpPresenter(IStartUpView iView) {
        super(iView);
        this.iStartUpView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
//        this.mGetConfigurationDataModel = new GetConfigurationDataModel(configDataIBaseModelListener);
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
//        callConfigurationApi();

        if (!(isDataDownloaded())) {
            callApi();
        }
    }


    /***/
    private void callApi() {
        if (iStartUpView.getCodeSnippet().hasNetworkConnection()) {
            SearchHomeRequest searchHomeRequest = new SearchHomeRequest();
            searchHomeRequest.setPage(0);
            searchHomeRequest.setUserId("15");
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<CompanyItemInfo>> call = apiService.callInstrument(searchHomeRequest);
            RetroFitBase.getInstance().retrofitEnqueue(call, mResponseListener, Constants.LoadCompanyList.LOAD_DAY);
        } else {
            iStartUpView.showNetworkMessage();
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
                iStartUpView.startDelayPriceUpdateService();
                mAppConfigurationManager.setSyncStatus(true);
            }
        }.execute();

    }


    /***/
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

        }

        @Override
        public void onFailure(Throwable throwable, int flag) {


        }

        @Override
        public void showDialog(String response, int flag) {


        }

        @Override
        public void showErrorDialog(String errorResponse, int flag) {

        }

        @Override
        public void showInternalServerErrorDialog(String errorResponse, int flag) {

        }

        @Override
        public void logOut(int flag) {

        }
    };

//    /***/
//    private void callConfigurationApi() {
//        if (iStartUpView.getCodeSnippet().hasNetworkConnection()) {
//            mGetConfigurationDataModel.getConfigData();
//        }
//    }

    /***/
    private boolean isDataDownloaded() {
        return new AppConfigurationManager(iStartUpView.getActivity()).getSyncStatus();
    }

//    /***/
//    private IBaseModelListener<ConfigData> configDataIBaseModelListener = new IBaseModelListener<ConfigData>() {
//        @Override
//        public void onSuccessfulApi(long taskId, ConfigData response) {
//            if (response != null) {
//                mAppConfigurationManager.setConfigData(iStartUpView.getCodeSnippet().getJsonStringFromObject(response, ConfigData.class));
//                mAppConfigurationManager.setConfigurationDataUpdated(true);
//            }
//        }
//
//        @Override
//        public void onFailureApi(long taskId, CustomException e) {
//
//        }
//    };


}
