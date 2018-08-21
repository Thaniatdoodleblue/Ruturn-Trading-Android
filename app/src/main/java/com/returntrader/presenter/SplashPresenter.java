package com.returntrader.presenter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.returntrader.common.Constants;
import com.returntrader.common.RetroFitBase;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.database.DaoSession;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.library.Log;
import com.returntrader.model.common.ConfigData;
import com.returntrader.model.common.UserInfo;
import com.returntrader.model.dto.request.SearchHomeRequest;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.ApiClient;
import com.returntrader.model.webservice.ApiInterface;
import com.returntrader.model.webservice.GetConfigurationDataModel;
import com.returntrader.model.webservice.executor.IGeneralResponseListener;
import com.returntrader.presenter.ipresenter.ISplashPresenter;
import com.returntrader.view.activity.EmailVerificationSuccessActivity;
import com.returntrader.view.activity.HomeActivity;
import com.returntrader.view.activity.StartUpActivity;
import com.returntrader.view.iview.ISplashView;

import java.util.List;

import retrofit2.Call;

/**
 * Created by moorthy on 11/14/2017.
 */

public class SplashPresenter extends BasePresenter implements ISplashPresenter {
    private ISplashView iSplashView;
    private AppConfigurationManager mAppConfigurationManager;
    private boolean isDeepLinkingEnabled = false;
    private GetConfigurationDataModel mGetConfigurationDataModel;
    private Handler mHandler;
    private Runnable mRunnable;

    public SplashPresenter(ISplashView iView) {
        super(iView);
        this.iSplashView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        this.mGetConfigurationDataModel = new GetConfigurationDataModel(configDataIBaseModelListener);
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        callConfigurationApi();
        checkStatus();

        if (!(isDataDownloaded())) {
            callApi();
        } else {
            iSplashView.startDelayPriceUpdateService();
        }

        iSplashView.navigateToNexScreen(Constants.Common.SPLASH_TIME_OUT);
    }

    /***/
    private void callConfigurationApi() {
        if (iSplashView.getCodeSnippet().hasNetworkConnection()) {
            mGetConfigurationDataModel.getConfigData();
        }
    }

    /***/
    private IBaseModelListener<ConfigData> configDataIBaseModelListener = new IBaseModelListener<ConfigData>() {
        @Override
        public void onSuccessfulApi(long taskId, ConfigData response) {
            if (response != null) {
                mAppConfigurationManager.setConfigData(iSplashView.getCodeSnippet().getJsonStringFromObject(response, ConfigData.class));
                mAppConfigurationManager.setConfigurationDataUpdated(true);
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {

        }
    };

    /***/
    private void callApi() {
        if (iSplashView.getCodeSnippet().hasNetworkConnection()) {
            SearchHomeRequest searchHomeRequest = new SearchHomeRequest();
            searchHomeRequest.setPage(0);
            searchHomeRequest.setUserId("15");
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<CompanyItemInfo>> call = apiService.callInstrument(searchHomeRequest);
            RetroFitBase.getInstance().retrofitEnqueue(call, mResponseListener, Constants.LoadCompanyList.LOAD_DAY);
        } else {
            iSplashView.getCodeSnippet().hasNetworkConnection();
        }
    }


    /***/
    private boolean isDataDownloaded() {
        return mAppConfigurationManager.getSyncStatus();
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
                iSplashView.startDelayPriceUpdateService();
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

    @Override
    public Intent getNextScreenActivity() {
        if (isDeepLinkingEnabled) {
            return new Intent(iSplashView.getActivity(), EmailVerificationSuccessActivity.class);
        }

        Intent intent = new Intent(iSplashView.getActivity(), StartUpActivity.class);
        boolean hasFullRegisterUser = !(TextUtils.isEmpty(mAppConfigurationManager.getUserId()));
        boolean isPartialUser = mAppConfigurationManager.isPartialRegisterCompleted();
        boolean hasPin = !(TextUtils.isEmpty(mAppConfigurationManager.getAuthenticationPin()));
        if (isPartialUser || (hasFullRegisterUser && hasPin)) {
            intent = new Intent(iSplashView.getActivity(), HomeActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }


    /***/
    private boolean isEmailAdded() {
        UserInfo userInfo = iSplashView.getCodeSnippet().getObjectFromJson(mAppConfigurationManager.getPartialUserInfo(), UserInfo.class);
        if (userInfo == null) {
            return false;
        }

        if (TextUtils.isEmpty(userInfo.getEmailId())) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public boolean isPartialAuthorizedUser() {
        if (TextUtils.isEmpty(mAppConfigurationManager.getUserId()) && !(mAppConfigurationManager.isPartialRegisterCompleted())) {
            return isEmailAdded();
        }
        return false;
    }

    @Override
    public void setDeepLinkingEnabled(boolean isDeepLinkingEnabled) {
        this.isDeepLinkingEnabled = isDeepLinkingEnabled;
    }


    /***/
    private void checkStatus() {
        //  mAppConfigurationManager.setAddressCompletedStatus(false);

        if (TextUtils.isEmpty(mAppConfigurationManager.getUserId()) || TextUtils.isEmpty(mAppConfigurationManager.getAuthenticationPin())) {
            return;
        }

        Log.d(TAG, "isAddressCompleted() " + mAppConfigurationManager.isAddressCompleted());
        Log.d(TAG, "isFicaVerified() " + mAppConfigurationManager.isFicaVerified());
        Log.d(TAG, "isFicaDetailCompleted() " + mAppConfigurationManager.isFicaDetailCompleted());
        Log.d(TAG, "isAccountVerified() " + mAppConfigurationManager.isAccountVerified());

        if (!(mAppConfigurationManager.isAddressCompleted())) {
            iSplashView.startStatusCheckService(Constants.AccountSync.CHECK_ADDRESS_STATUS);
        }

        if (!(mAppConfigurationManager.isFicaVerified())) {
            iSplashView.startStatusCheckService(Constants.AccountSync.CHECK_FICA_STATUS);
        }

        if (mAppConfigurationManager.isFicaDetailCompleted()) {
            if (!(mAppConfigurationManager.isAccountVerified())) {
                iSplashView.startStatusCheckService(Constants.AccountSync.CHECK_TRUSTED_ACCOUNT_STATUS);
            }
        }

        if (mAppConfigurationManager.isFicaVerified()) {
            iSplashView.startStatusCheckService(Constants.AccountSync.SYNC_BALANCE);
        }
    }
}
