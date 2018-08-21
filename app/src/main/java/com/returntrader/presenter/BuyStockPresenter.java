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
import com.returntrader.model.common.StockHold;
import com.returntrader.model.dto.request.BuyRequest;
import com.returntrader.model.dto.response.BuyResponse;
import com.returntrader.model.dto.response.StockHoldResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.BuyModel;
import com.returntrader.presenter.ipresenter.IBuyStockPresenter;
import com.returntrader.view.iview.IBuyStockView;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by moorthy on 9/20/2017.
 */

public class BuyStockPresenter extends BasePresenter implements IBuyStockPresenter {
    private IBuyStockView iBuyStockView;
    private StockHold mCurrentStock;
    private BuyModel mBuyModel;
    private String mCurrentSharePrice;
    private AppConfigurationManager mAppConfigurationManager;
    private DatabaseManager mDatabaseManager;


    public BuyStockPresenter(IBuyStockView iView) {
        super(iView);
        this.iBuyStockView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        this.mBuyModel = new BuyModel(buyResponseIBaseModelListener);
        this.mDatabaseManager = new DatabaseManager(iView.getActivity());

    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            mCurrentStock = bundle.getParcelable(Constants.BundleKey.BUNDLE_STOCK_ITEM);
            if (mCurrentStock != null) {
                iBuyStockView.setStockDetails(getCurrentStock());
            }
        }
    }


    @Override
    public StockHold getCurrentStock() {
        return mCurrentStock;
    }

    private BigDecimal totalInvestment, totalShare, totalHolding;

    @Override
    public void doDelayPriceUpdate() {
        StockHoldResponse response = iBuyStockView.getCodeSnippet().getObjectFromJsonStringRetro(new AppConfigurationManager(iBuyStockView.getActivity()).getHoldingData(), StockHoldResponse.class);
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

    @Override
    public void setAuthNeeded() {
        mAppConfigurationManager.setPinAuthenticationRequired(true);
        mAppConfigurationManager.setFingerPrintEnabledStatus(true);
    }

    /***/
    private void setCashAndInvestedDetails() {
        iBuyStockView.setUpTopBar();
    }

    private void doBuyApi(String orderAmount) {
        BigDecimal convertedPrice = TraderApplication.getInstance().getBuyAmount(orderAmount);
        if (iBuyStockView.getCodeSnippet().hasNetworkConnection()) {
            iBuyStockView.showProgressbar();
            mBuyModel.doBuyShare(1, new BuyRequest(mAppConfigurationManager.getUserId(), String.format("%.0f",convertedPrice), getCurrentStock().getIsinCode()));
        } else {
            iBuyStockView.showNetworkMessage();
        }
    }


    private IBaseModelListener<BuyResponse> buyResponseIBaseModelListener = new IBaseModelListener<BuyResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, BuyResponse response) {
            iBuyStockView.dismissProgressbar();
            if (response != null) {
                if (response.isProcessed()) {
                    iBuyStockView.doUpdateBalance();
                    iBuyStockView.postOrderProgress(Constants.TransactionType.RESPONSE_SUCCESS);
                    if (!mAppConfigurationManager.isMarketAvailable()) {
                        iBuyStockView.showMessage("You are currently trading outstanding trading hours - trades would be proceed next working day");
                    }
                }
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iBuyStockView.dismissProgressbar();
            iBuyStockView.postOrderProgress(Constants.TransactionType.RESPONSE_FAILURE);
        }
    };


    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE:
                if (resultCode == Activity.RESULT_OK) {
                    doBuyApi(mCurrentSharePrice);
                    iBuyStockView.showQuestions();
                } else {
                    iBuyStockView.showMessage(iBuyStockView.getActivity().getString(R.string.txt_authenticationbuy_failed));
                }
                break;
        }
    }

    @Override
    public void calculateTransactionCost(String amountShare) {
        mCurrentSharePrice = amountShare;
        String transactionTaxCost = TraderApplication.getInstance().getTransactionTaxCost(amountShare, Constants.TransactionType.TRANSACTION_BUY);
        String totalAmount = TraderApplication.getInstance().getTotalAmount(amountShare, Constants.TransactionType.TRANSACTION_BUY);
        iBuyStockView.setTotalTransactionCost(transactionTaxCost, totalAmount);
    }

    @Override
    public boolean isValidShareAmount() {
        return getCurrentStock() != null && getCurrentStock().getClosingPrice() > 0;
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
    public boolean isMarketAvailable() {
        return mAppConfigurationManager.isMarketAvailable();
    }


}
