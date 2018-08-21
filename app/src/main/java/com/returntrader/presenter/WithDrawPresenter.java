package com.returntrader.presenter;

import android.os.AsyncTask;
import android.os.Bundle;

import com.annimon.stream.Stream;
import com.returntrader.common.RetroFitBase;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.database.DatabaseManager;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.library.Log;
import com.returntrader.model.common.StockHold;
import com.returntrader.model.common.WithDrawableBalanceData;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.request.WithDrawalRequest;
import com.returntrader.model.dto.response.BankVerifyResponse;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.dto.response.StockHoldResponse;
import com.returntrader.model.dto.response.WithDrawBalanceResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.ApiClient;
import com.returntrader.model.webservice.ApiInterface;
import com.returntrader.model.webservice.WithDrawBalanceModel;
import com.returntrader.model.webservice.WithDrawalModel;
import com.returntrader.model.webservice.executor.IGeneralResponseListener;
import com.returntrader.presenter.ipresenter.IWithDrawPresenter;
import com.returntrader.view.iview.IWithDrawView;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;

import static com.returntrader.common.Constants.RequestCodes.TASKID_VERIFYBANK;

/**
 * Created by moorthy on 12/1/2017.
 */

public class WithDrawPresenter extends BasePresenter implements IWithDrawPresenter {

    private IWithDrawView iWithDrawView;
    private AppConfigurationManager mAppConfigurationManager;
    private WithDrawalModel mWithDrawalModel;
    private DatabaseManager mDatabaseManager;
    private WithDrawBalanceModel withDrawBalanceModel;
    private WithDrawableBalanceData withDrawableBalanceData;

    public WithDrawPresenter(IWithDrawView iView) {
        super(iView);
        this.iWithDrawView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        this.mWithDrawalModel = new WithDrawalModel(iWithdrawModelListener);
        this.withDrawBalanceModel = new WithDrawBalanceModel(responseIBaseModelListener);
        this.mDatabaseManager = new DatabaseManager(iWithDrawView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
      callWithDrawableBalanceApi();

    }

    @Override
    public void callWithDrawableBalanceApi() {
        if (iWithDrawView.getCodeSnippet().hasNetworkConnection()) {
            withDrawBalanceModel.withDrawBalance(1, new BaseRequest(mAppConfigurationManager.getUserId()));
        } else {
            iWithDrawView.showNetworkMessage();
        }
    }


    /***/
    private void callVerifyBankAPI() {
        if (iWithDrawView.getCodeSnippet().hasNetworkConnection()) {
            iWithDrawView.showProgressbar();
            BaseRequest request = new BaseRequest();
            request.setUserId(mAppConfigurationManager.getUserId());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<BankVerifyResponse>> call = apiService.verifyBank(request);
            RetroFitBase.getInstance().retrofitEnqueueVerifyBank(call, mResponseListener, TASKID_VERIFYBANK);
        } else {
            iWithDrawView.showNetworkMessage();
        }
    }


    /***/
    private IGeneralResponseListener<List<BankVerifyResponse>> mResponseListener = new IGeneralResponseListener<List<BankVerifyResponse>>() {
        @Override
        public void onSuccess(List<BankVerifyResponse> accountsList, int flag) {
            iWithDrawView.dismissProgressbar();
            if (accountsList == null || accountsList.size() < 0) {
                return;
            }

            if (accountsList.get(0).getVerificationStatus().equalsIgnoreCase("VerificationSucceeded")) {
                doProceedRequestWithdrawal();
            } else {
                iWithDrawView.showMessage("Your bank account is not yet verified.");
            }
        }

        @Override
        public void onFailure(String response, int flag) {
            iWithDrawView.dismissProgressbar();
        }

        @Override
        public void onFailure(Throwable throwable, int flag) {
            iWithDrawView.dismissProgressbar();
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

    /***/
    private IBaseModelListener<BaseResponse> iWithdrawModelListener = new IBaseModelListener<BaseResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, BaseResponse response) {
            iWithDrawView.dismissProgressbar();
            if (response != null) {
                iWithDrawView.showMessage(response.getMessage());
                iWithDrawView.showNoticeDialog();
                iWithDrawView.doUpdateBalance();
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iWithDrawView.dismissProgressbar();
            if (e != null) {
                iWithDrawView.showMessage(e.getException());
            }
        }
    };

    /***/
    private IBaseModelListener<WithDrawBalanceResponse> responseIBaseModelListener = new IBaseModelListener<WithDrawBalanceResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, WithDrawBalanceResponse response) {
            if (response != null) {
                withDrawableBalanceData = response.getData();
                iWithDrawView.setAccountInfo(withDrawableBalanceData);
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            if (e != null) {
                iWithDrawView.showMessage(e.getException());
            }
        }
    };

    @Override
    public String getLastKnownBalance() {
       // return mAppConfigurationManager.getLastKnownBalance();
        return iWithDrawView.getCodeSnippet().getDecimalPoint(withDrawableBalanceData.getWithdrawableCash());
    }

    private WithDrawalRequest request;
    @Override
    public void doRequestWithdrawal(WithDrawalRequest request) {
        this.request = request;
        callVerifyBankAPI();
    }

    /***/
    private void doProceedRequestWithdrawal() {
        request.setUserId(mAppConfigurationManager.getUserId());
        if (iWithDrawView.getCodeSnippet().hasNetworkConnection()) {
            iWithDrawView.showProgressbar();
            mWithDrawalModel.withDrawals(0, request);
        } else {
            iWithDrawView.showNetworkMessage();
        }
    }

    @Override
    public void doAuthentication() {
        mAppConfigurationManager.setPinAuthenticationRequired(true);
        mAppConfigurationManager.setFingerPrintEnabledStatus(true);
        iWithDrawView.doAuthentication();
    }

    @Override
    public void setAuthPinRequired(boolean isRequired) {
        mAppConfigurationManager.setPinAuthenticationRequired(isRequired);
        mAppConfigurationManager.setFingerPrintEnabledStatus(isRequired);
    }


    private BigDecimal totalInvestment, totalShare, totalHolding;

    @Override
    public void doDelayPriceUpdate() {
        StockHoldResponse response = iWithDrawView.getCodeSnippet().getObjectFromJsonStringRetro(new AppConfigurationManager(iWithDrawView.getActivity()).getHoldingData(), StockHoldResponse.class);
        if (response != null) {
            /*Total Holdings = No of shares bought for a company * 15 mins delayed price*/
            /* Invested Amount = No of shares bought for a compancy * Share price ( baseCost )*/
            /*differece = TH > IA = green : red*/
            /*Whole Invested amount : add all invested amount */
            /*shares = add all shares.*/
            List<StockHold> stockHoldList = response.getStockHoldList();
            if (stockHoldList != null) {
                totalInvestment = totalShare = totalHolding = BigDecimal.ZERO;
                new AsyncTask<Void, Void, List<StockHold>>() {
                    @Override
                    protected List<StockHold> doInBackground(Void... lists) {
                        Stream.of(stockHoldList).forEach(stock -> {
                            CompanyItemInfo info = mDatabaseManager.getCompanyItem(stock.getIsinCode());
                            if (info != null) {
                                stock.setCompanyLogo(info.getCompanyImageUrl());
                                stock.setClosingPrice(info.getLastPrice());
                                stock.setLastUpdatedPrice(info.getLastKnownDelayPrice());
                                BigDecimal currentInvestedAmount = new BigDecimal(TraderApplication.getInstance().getInvestedAmountBySharePrice(stock.getShares(), String.valueOf(stock.getBaseCost())));
                                BigDecimal currentHoldingAmount = new BigDecimal(TraderApplication.getInstance().getTotalHoldingByPrice(stock.getShares(), String.valueOf(info.getLastPrice())));

                                Log.d("B InvestedAmount", "" + currentInvestedAmount);
                                Log.d("B InvestedAmount", "" + currentHoldingAmount);

                                stock.setCurrentInvestment(String.valueOf(currentInvestedAmount));
                                stock.setCurrentHolding(String.valueOf(currentHoldingAmount));
                                totalInvestment = totalInvestment.add(currentInvestedAmount);
                                totalShare = totalShare.add(new BigDecimal(stock.getShares()));
                                totalHolding = totalHolding.add(currentHoldingAmount);
//                                totalInvestment[0] = totalInvestment[0].add(currentInvestedAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
//                                totalHolding[0] = totalHolding[0].add(currentHoldingAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
//                                totalShare[0] = totalShare[0].add(new BigDecimal(stock.getShares())).setScale(2, BigDecimal.ROUND_HALF_UP);
                            }
                        });
                        Log.d("B InvestedAmountToT", "" + totalInvestment);
                        mAppConfigurationManager.setLastKnownHoldingAmount(String.valueOf(totalHolding));
                        mAppConfigurationManager.setLastKnownInvestedAmount(String.valueOf(totalInvestment));
                        mAppConfigurationManager.setLastKnownShare(String.valueOf(totalShare));
                        return stockHoldList;
                    }

                    @Override
                    protected void onPostExecute(List<StockHold> stockHoldsList) {
                        super.onPostExecute(stockHoldsList);
                        setCashAndInvestedDetails();
                    }
                }.execute();
            }
        }
    }

    private void setCashAndInvestedDetails() {
        /*if we are having the toolbar we can update it but for game its not required so for,infure it may get added*/
    }
}
