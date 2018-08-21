package com.returntrader.sync;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.annimon.stream.Stream;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.BalanceItem;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.response.BalanceResponse;
import com.returntrader.model.dto.response.CheckAddressStatusResponse;
import com.returntrader.model.dto.response.StockHoldResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.BalanceModel;
import com.returntrader.model.webservice.CheckAddressStatusModel;
import com.returntrader.model.webservice.GetHoldingModel;
import com.returntrader.utils.CodeSnippet;

import java.util.NoSuchElementException;

/**
 * Created by moorthy on 10/9/2017.
 */

public class SyncAccountDetailService extends IntentService {
    private BalanceModel mBalanceModel;
    private CodeSnippet mCodeSnippet;
    private AppConfigurationManager mAppConfigurationManager;
    private String TAG = getClass().getSimpleName();
    private CheckAddressStatusModel mCheckAddressStatusModel;
    private GetHoldingModel mGetHoldingModel;


    public SyncAccountDetailService() {
        super("SyncAccountDetailService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mAppConfigurationManager = new AppConfigurationManager(this);
        this.mBalanceModel = new BalanceModel(balanceResponseIBaseModelListener);
        this.mGetHoldingModel = new GetHoldingModel(stockHoldResponse);
        this.mCheckAddressStatusModel = new CheckAddressStatusModel(checkAddressStatusResponseListener);
    }


    private void sendBalanceUpdatedBroadCast() {
        Intent broadCastIntent = new Intent();
        broadCastIntent.setAction(Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED);
        broadCastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadCastIntent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        int syncType = bundle.getInt(Constants.AccountSync.BUNDLE_SYNC_TYPE, -1);
        switch (syncType) {
            case Constants.AccountSync.SYNC_BALANCE:
                updateBalance();
                break;
            case Constants.AccountSync.CHECK_ADDRESS_STATUS:
                checkStatus(Constants.AccountSync.CHECK_ADDRESS_STATUS);
                break;
            case Constants.AccountSync.CHECK_FICA_STATUS:
                checkStatus(Constants.AccountSync.CHECK_FICA_STATUS);
                break;
            case Constants.AccountSync.CHECK_TRUSTED_ACCOUNT_STATUS:
                checkStatus(Constants.AccountSync.CHECK_TRUSTED_ACCOUNT_STATUS);
                break;
        }
    }


    private CodeSnippet getCodeSnippet() {
        if (mCodeSnippet == null) {
            mCodeSnippet = new CodeSnippet(this);
        }
        return mCodeSnippet;
    }


    /***/
    private void updateBalance() {
        if (getCodeSnippet().hasNetworkConnection()) {
            if (!TextUtils.isEmpty(mAppConfigurationManager.getUserId())) {
                if (mAppConfigurationManager.isFicaVerified()) {
                    Log.d("BalanceAPI Called", "From Service");
                    mBalanceModel.getBalance(new BaseRequest(mAppConfigurationManager.getUserId()));
                }
            }
        }
    }


    /***/
    private void checkStatus(int syncType) {
        if (getCodeSnippet().hasNetworkConnection()) {
            BaseRequest baseRequest = new BaseRequest(mAppConfigurationManager.getUserId());
            switch (syncType) {
                case Constants.AccountSync.CHECK_ADDRESS_STATUS:
                    mCheckAddressStatusModel.checkAddressStatus(baseRequest);
                    break;
                case Constants.AccountSync.CHECK_FICA_STATUS:
                    mCheckAddressStatusModel.checkFicaStatus(baseRequest);
                    break;
                case Constants.AccountSync.CHECK_TRUSTED_ACCOUNT_STATUS:
                    Log.d(TAG, "calling trusted account");
                    mCheckAddressStatusModel.checkTrustedAccountStatus(baseRequest);
                    break;
            }
        }
    }


    private boolean getStatus(int result) {
        return result == 1;
    }

    private IBaseModelListener<CheckAddressStatusResponse> checkAddressStatusResponseListener = new IBaseModelListener<CheckAddressStatusResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, CheckAddressStatusResponse response) {
            Log.d(TAG, "onSuccessfulApi taskId :  " + taskId);
            if (!(response.isSuccess())) {
                return;
            }
            switch ((int) taskId) {
                case Constants.SyncServiceTaskId.SYNC_CHECK_ADDRESS_STATUS:
                    Log.d(TAG, " getIsAddressCompleted :" + getStatus(response.getIsAddressCompleted()));
                    mAppConfigurationManager.setAddressCompletedStatus(getStatus(response.getIsAddressCompleted()));
                    break;
                case Constants.SyncServiceTaskId.SYNC_CHECK_FICA_STATUS:
                    Log.d("Running SyncResp", "" + response.getIsFicaVerified());
                    switch (response.getIsFicaVerified()) {
                        case Constants.FicaContentType.FICA_NOT_UPLOAD_COMPLETE: // fica not uploaded
                            mAppConfigurationManager.setFicaDetailCompletedStatus(false);
                            mAppConfigurationManager.setFicaVerifiedStatus(false);
                            break;
                        case Constants.FicaContentType.FICA_UPLOAD_COMPLETE: // fica uploaded
                            mAppConfigurationManager.setFicaDetailCompletedStatus(true);
                            mAppConfigurationManager.setFicaVerifiedStatus(false);
                            break;
                        case Constants.FicaContentType.FICA_DOC_VERIFIED: // fica verified
                            mAppConfigurationManager.setFicaVerifiedStatus(true);
                            mAppConfigurationManager.setAddressCompletedStatus(true);
                            break;
                    }

                    break;
                case Constants.SyncServiceTaskId.SYNC_CHECK_TRUSTED_ACCOUNT_STATUS:
                    // this will allow user to make buy and sell.
                    mAppConfigurationManager.setAccountVerificationStatus(true);
                    break;
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            Log.d(TAG, "error occurred in  taskId : " + taskId + " in sync service");
        }
    };


    /***/
    private IBaseModelListener<BalanceResponse> balanceResponseIBaseModelListener = new IBaseModelListener<BalanceResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, BalanceResponse response) {
            if (response.getBalanceItems() != null) {
                if (response.getBalanceItems().size() > 0) {
                    try {
                        BalanceItem balanceItem = Stream.of(response.getBalanceItems())
                                .filter(balance -> balance.getAccountType().equalsIgnoreCase(mAppConfigurationManager.getTrustAccountType()))
                                .single();
                        if (balanceItem != null) {
                            mAppConfigurationManager.setBalanceUpdatedStatus(true);
                            mAppConfigurationManager.setEftReferenceNumber(balanceItem.getEftReference());
                            Log.d("Balance From Balance", "" + balanceItem.getBalance());
                            mAppConfigurationManager.setLastKnownBalance(String.valueOf(TraderApplication.getInstance().convertPriceCentToRand(balanceItem.getBalance())));
                            sendBalanceUpdatedBroadCast();
                        }
                    } catch (IllegalStateException | NoSuchElementException e) {
                        e.printStackTrace();
                    }
                }
            }
            callHoldingApi();
        }


        @Override
        public void onFailureApi(long taskId, CustomException e) {
            stopSelf();
        }
    };

    /***/
    private IBaseModelListener<StockHoldResponse> stockHoldResponse = new IBaseModelListener<StockHoldResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, StockHoldResponse response) {
            if (response.getStockHoldList() != null) {
            //    Log.d("Saved Holding Data", "" + response.getStockHoldList().get(0).getBaseCostStr());
                mAppConfigurationManager.setHoldingData(getCodeSnippet().getJsonStringFromObject(response));
            }
           // Log.d("Saved Holding Data", "" + mAppConfigurationManager.getHoldingData());
            sendBroadCastComplete();
            stopSelf();
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            stopSelf();
        }
    };

    /***/
    private void sendBroadCastComplete() {
        Log.d(TAG, "sendBroadCastComplete");
        Intent broadCastIntent = new Intent();
        broadCastIntent.setAction(Constants.BroadCastKey.ACTION_HOLDING_UPDATEINFO);
        broadCastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadCastIntent);
    }

    /***/
    private void callHoldingApi() {
        Log.d(TAG, "callHoldingApi isAccountVerified() " + mAppConfigurationManager.isFicaVerified());
        if (getCodeSnippet().hasNetworkConnection()) {
            if (mAppConfigurationManager.isFicaVerified()) {
                mGetHoldingModel.getHolding(0, new BaseRequest(mAppConfigurationManager.getUserId()));
            }
        }
    }
}
