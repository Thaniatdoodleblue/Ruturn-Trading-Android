package com.returntrader.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.annimon.stream.Stream;
import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.database.DatabaseManager;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.SellNetAmount;
import com.returntrader.model.common.StockHold;
import com.returntrader.model.dto.request.BuyRequest;
import com.returntrader.model.dto.response.SellResponse;
import com.returntrader.model.dto.response.StockHoldResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.SellModel;
import com.returntrader.presenter.ipresenter.ISellStockPresenter;
import com.returntrader.view.iview.ISellStockView;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by moorthy on 9/20/2017.
 */

public class SellStockPresenter extends BasePresenter implements ISellStockPresenter {
    private ISellStockView iSellStockView;
    private StockHold mCurrentStock;
    private SellModel mSellModel;
    private AppConfigurationManager mAppConfigurationManager;
    private DatabaseManager mDatabaseManager;

    public SellStockPresenter(ISellStockView iView) {
        super(iView);
        this.iSellStockView = iView;
        this.mSellModel = new SellModel(sellResponseIBaseModelListener);
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        this.mDatabaseManager = new DatabaseManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            mCurrentStock = bundle.getParcelable(Constants.BundleKey.BUNDLE_STOCK_ITEM);
            if (mCurrentStock != null) {
                iSellStockView.setStockDetails(getCurrentStock());
                calculatePercentage(mCurrentStock.getCurrentHolding());
            }
        }
    }


    private StockHold getCurrentStock() {
        return mCurrentStock;
    }

    @Override
    public void calculatePercentage(String value) {
        try {
            if (TextUtils.isEmpty(value)) {
                value = Constants.Common.DEFAULT_PRICE_ZERO;
            }

            BigDecimal lastKnownUpdatedPrice = new BigDecimal(TextUtils.isEmpty(mCurrentStock.getCurrentHolding()) ? "0.0" : iSellStockView.getCodeSnippet().doReplaceComma(mCurrentStock.getCurrentHolding().toString()));
            BigDecimal userAmount = new BigDecimal(value);
            float multipleValue = (userAmount.floatValue() / lastKnownUpdatedPrice.floatValue());
            float percentage = (multipleValue * 100);
            iSellStockView.setPercentageValue(String.valueOf(percentage));
            calculateNetTotal(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void calculateRand(String percentageValue) {
        if (TextUtils.isEmpty(percentageValue)) {
            percentageValue = Constants.Common.DEFAULT_PRICE_ZERO;
        }

        BigDecimal lastKnownUpdatedPrice = new BigDecimal(TextUtils.isEmpty(mCurrentStock.getCurrentHolding()) ? "0.0" : iSellStockView.getCodeSnippet().doReplaceComma(mCurrentStock.getCurrentHolding().toString()));
        BigDecimal userAmount = new BigDecimal(percentageValue);
        float multipleValue = (userAmount.floatValue() / 100);
        float percentage = (multipleValue * lastKnownUpdatedPrice.floatValue());
        iSellStockView.setRandValue(String.valueOf(percentage));
        calculateNetTotal(String.valueOf(percentage));
    }


    @Override
    public BigDecimal getCurrentAvailableSell() {
        String currentAvailSellStr = iSellStockView.getCodeSnippet().getDecimalPoint(mCurrentStock.getCurrentHolding());
        return new BigDecimal(TextUtils.isEmpty(currentAvailSellStr) ? "0.0" : iSellStockView.getCodeSnippet().doReplaceComma(currentAvailSellStr));
    }

    @Override
    public boolean isMarketAvailable() {
        return mAppConfigurationManager.isMarketAvailable();
    }

    private BigDecimal totalInvestment, totalShare, totalHolding;

    @Override
    public void doDelayPriceUpdate() {
        StockHoldResponse response = iSellStockView.getCodeSnippet().getObjectFromJsonStringRetro(new AppConfigurationManager(iSellStockView.getActivity()).getHoldingData(), StockHoldResponse.class);
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

                        com.returntrader.library.Log.d("B InvestedAmountToT", "" + totalInvestment);
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

    @Override
    public void setAuthNeeded() {
        mAppConfigurationManager.setPinAuthenticationRequired(true);
        mAppConfigurationManager.setFingerPrintEnabledStatus(true);
    }

    /***/
    private void setCashAndInvestedDetails() {
        iSellStockView.setUpTopBar();
    }

    private String getNetAmount(String userSellAmount, String transactionCost) {

        BigDecimal totalAmount = new BigDecimal(Constants.Common.DEFAULT_PRICE_ZERO);
        totalAmount = new BigDecimal(userSellAmount).subtract(new BigDecimal(transactionCost)).setScale(2, BigDecimal.ROUND_HALF_UP);
        return String.valueOf(totalAmount);
    }


    private void calculateNetTotal(String userSellAmount) {
        String transactionCost = TraderApplication.getInstance().getTransactionTaxCost(userSellAmount, Constants.TransactionType.TRANSACTION_SELL);
        SellNetAmount sellNetAmount = new SellNetAmount();
        sellNetAmount.setGrossAmount(userSellAmount);
        sellNetAmount.setInvestmentCost(transactionCost);
        sellNetAmount.setNetAmount(getNetAmount(userSellAmount, transactionCost));
        iSellStockView.setNetAmount(sellNetAmount);
    }


    private void doSellShare() {
        Log.d(TAG, "Last known sell price percentage : " + iSellStockView.getSellPercentage());
        BuyRequest sellRequest = new BuyRequest();
        sellRequest.setIsinCode(getCurrentStock().getIsinCode());
        sellRequest.setOrderValue(iSellStockView.getSellPercentage());
        sellRequest.setUserId(mAppConfigurationManager.getUserId());
        if (iSellStockView.getCodeSnippet().hasNetworkConnection()) {
            iSellStockView.showProgressbar();
            mSellModel.doSellShare(0, sellRequest);
        } else {
            iSellStockView.showNetworkMessage();
        }
    }

    /***/
    private IBaseModelListener<SellResponse> sellResponseIBaseModelListener = new IBaseModelListener<SellResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, SellResponse response) {
            iSellStockView.dismissProgressbar();
            if (response != null) {
                if (response.isProcessed()) {
                    iSellStockView.doBalanceUpdate();
                    iSellStockView.postOrderProgress(Constants.TransactionType.RESPONSE_SUCCESS);
                    if (!mAppConfigurationManager.isMarketAvailable()) {
                        iSellStockView.showMessage("You are currently trading outstanding trading hours - trades would be proceed next working day");
                    }
                }
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iSellStockView.dismissProgressbar();
            iSellStockView.postOrderProgress(Constants.TransactionType.RESPONSE_FAILURE);
        }
    };


    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE:
                if (resultCode == Activity.RESULT_OK) {
                    doSellShare();
                    iSellStockView.showQuestions();
                } else {
                    iSellStockView.showMessage(iSellStockView.getActivity().getString(R.string.txt_authenticationsell_failed));
                }
                break;
        }
    }


    @Override
    public String getLastKnownBalance() {
        return mAppConfigurationManager.getLastKnownBalance();
    }
}
