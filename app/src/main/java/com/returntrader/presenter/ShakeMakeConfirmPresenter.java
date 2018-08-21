package com.returntrader.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.annimon.stream.Stream;
import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.database.DatabaseManager;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.library.Log;
import com.returntrader.model.common.ShakeMakeCompanyData;
import com.returntrader.model.common.ShakeMakeGroupData;
import com.returntrader.model.common.ShakeMakeMoneyData;
import com.returntrader.model.common.StockHold;
import com.returntrader.model.dto.request.BuyRequest;
import com.returntrader.model.dto.response.BuyResponse;
import com.returntrader.model.dto.response.StockHoldResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.BuyModel;
import com.returntrader.model.webservice.ShakeMakeCompanyModel;
import com.returntrader.presenter.ipresenter.IShakeMakeConfirmPresenter;
import com.returntrader.view.iview.IShakeMakeConfirmView;
import com.returntrader.view.iview.IView;

import java.math.BigDecimal;
import java.util.List;

public class ShakeMakeConfirmPresenter extends BasePresenter implements IShakeMakeConfirmPresenter {


    private IShakeMakeConfirmView iShakeMakeConfirmView;
    private AppConfigurationManager mAppConfigurationManager;
    private DatabaseManager mDatabaseManager;
    private BuyModel mBuyModel;
    private ShakeMakeCompanyData shakeMakeCompanyData;

    public ShakeMakeConfirmPresenter(IShakeMakeConfirmView iShakeMakeConfirmView) {
        super(iShakeMakeConfirmView);
        this.iShakeMakeConfirmView = iShakeMakeConfirmView;
        mAppConfigurationManager = new AppConfigurationManager(iShakeMakeConfirmView.getActivity());
        this.mBuyModel = new BuyModel(buyResponseIBaseModelListener);
        this.mDatabaseManager = new DatabaseManager(iShakeMakeConfirmView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            shakeMakeCompanyData = bundle.getParcelable(Constants.BundleKey.BUNDLE_SHAKE_MAKE_COMPANY_DATA);
            iShakeMakeConfirmView.setShakeMakeCompanyData(shakeMakeCompanyData);

        }
    }


    private BigDecimal totalInvestment, totalShare, totalHolding;
    @Override
    public void doDelayPriceUpdate() {
        StockHoldResponse response = iShakeMakeConfirmView.getCodeSnippet().getObjectFromJsonStringRetro(new AppConfigurationManager(iShakeMakeConfirmView.getActivity()).getHoldingData(), StockHoldResponse.class);
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

    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE:
                if (resultCode == Activity.RESULT_OK) {
                    doBuyApi(getShakeMakeMoneyData());
                } else {
                    /*error message*/
                    //iShakeMakeConfirmView.showMessage(iShakeMakeConfirmView.getActivity().getString(R.string.txt_authenticationbuy_failed));
                }
                break;
        }
    }



    @Override
    public void doBuyApi(ShakeMakeMoneyData shakeMakeMoneyData) {
        BigDecimal convertedPrice = TraderApplication.getInstance().getBuyAmount(shakeMakeMoneyData.getAmount());
        if (iShakeMakeConfirmView.getCodeSnippet().hasNetworkConnection()) {
            iShakeMakeConfirmView.showProgressbar();
            mBuyModel.doBuyShare(1, new BuyRequest(mAppConfigurationManager.getUserId(), String.format("%.0f",convertedPrice), shakeMakeCompanyData.getIsinCode()));
        } else {
            iShakeMakeConfirmView.showNetworkMessage();
        }
    }

    private IBaseModelListener<BuyResponse> buyResponseIBaseModelListener = new IBaseModelListener<BuyResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, BuyResponse response) {
            iShakeMakeConfirmView.dismissProgressbar();
            if (response != null) {
                if (response.isProcessed()) {
                    iShakeMakeConfirmView.doUpdateBalance();
                    setNavigatingToShakeMakeSuccess(iShakeMakeConfirmView.getActivity().getString(R.string.txt_shake_make_payment_sucees_msg) );
                    //if (!mAppConfigurationManager.isMarketAvailable()) {
                    //    iShakeMakeConfirmView.showMessage("You are currently trading outstanding trading hours - trades would be proceed next working day");
                    //}
                }
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iShakeMakeConfirmView.dismissProgressbar();
            iShakeMakeConfirmView.showMessage(e.getException());
            setNavigatingToShakeMakeSuccess(iShakeMakeConfirmView.getActivity().getString(R.string.txt_process_failed) );

        }
    };

    @Override
    public void setNavigatingToShakeMakeSuccess(String msg) {
        Bundle bundle =  new Bundle();
        bundle.putParcelable(Constants.BundleKey.BUNDLE_SHAKE_MAKE_COMPANY_DATA,shakeMakeCompanyData);
        bundle.putString(Constants.BundleKey.BUNDLE_SHAKE_MAKE_PAYMENT_STATUS,msg);
        iShakeMakeConfirmView.navigateToShakeMakeSuccess(bundle);
    }


    @Override
    public boolean hasTransactionAccess() {
        return mAppConfigurationManager.isFicaVerified();
    }

    @Override
    public String getLastKnownBalance() {
        return mAppConfigurationManager.getLastKnownBalance();
    }

    @Override
    public ShakeMakeMoneyData  getShakeMakeMoneyData(){
        return mAppConfigurationManager.getShakeMakeMoneyData();
    }

    @Override
    public boolean isMarketAvailable() {
        return mAppConfigurationManager.isMarketAvailable();
    }

    @Override
    public void setAuthNeeded(){
        mAppConfigurationManager.setPinAuthenticationRequired(true);
        mAppConfigurationManager.setFingerPrintEnabledStatus(true);
    }
}
