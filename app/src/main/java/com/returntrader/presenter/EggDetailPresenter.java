package com.returntrader.presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.annimon.stream.Stream;
import com.returntrader.adapter.HoldStockAdapter;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.database.DatabaseManager;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.StockHold;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.response.StockHoldResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.GetHoldingModel;
import com.returntrader.presenter.ipresenter.IEggDetailPresenter;
import com.returntrader.view.iview.IEggDetailView;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by moorthy on 9/15/2017.
 */

public class EggDetailPresenter extends BasePresenter implements IEggDetailPresenter {


    private IEggDetailView iEggDetailView;
    private GetHoldingModel mGetHoldingModel;
    private AppConfigurationManager mAppConfigurationManager;
    private HoldStockAdapter mHoldStockAdapter;
    private DatabaseManager mDatabaseManager;

    public EggDetailPresenter(IEggDetailView iView) {
        super(iView);
        this.iEggDetailView = iView;
        this.mDatabaseManager = new DatabaseManager(iView.getActivity());
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        this.mGetHoldingModel = new GetHoldingModel(stockHoldResponse);

    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        updateBalance();
        setInvestmentDetails();
        getHolding(false);
    }


    @Override
    public void updateBalance() {
        if (!(TextUtils.isEmpty(mAppConfigurationManager.getLastKnownBalance()))) {
            iEggDetailView.setBalanceData(mAppConfigurationManager.getLastKnownBalance());
        }

    }

    @Override
    public void setShowFingerPrint(boolean canShow) {
//        mAppConfigurationManager.setShowFingerPrint(canShow);
        mAppConfigurationManager.setFingerPrintEnabledStatus(canShow);
    }


    private void checkForBalance() {
        if (!(mAppConfigurationManager.isBalanceUpdated())) {
            callBalanceApi();
        }
    }

    private void setInvestmentDetails() {
        String investmentAmount = mAppConfigurationManager.getLastKnownInvestedAmount();
        String shares = mAppConfigurationManager.getLastKnownShare();
        if (!(TextUtils.isEmpty(investmentAmount)) && !(TextUtils.isEmpty(shares))) {
            iEggDetailView.setInvestmentDetails(investmentAmount, shares);
        } else if (!(TextUtils.isEmpty(shares))) {
            iEggDetailView.setInvestmentDetails(null, shares);
        } else if (!(TextUtils.isEmpty(investmentAmount))) {
            iEggDetailView.setInvestmentDetails(investmentAmount, null);
        }
    }

    private boolean isCheckBalanceAvailable() {
        return (mAppConfigurationManager.isBalanceUpdated()) && !(TextUtils.isEmpty(mAppConfigurationManager.getLastKnownBalance())) || new BigDecimal(mAppConfigurationManager.getLastKnownBalance()).compareTo(BigDecimal.ZERO) == 1;
    }

    @Override
    public void getHolding(boolean canCallHoldings) {
        if (canCallHoldings) {
            callHoldingApi();
        } else {
            StockHoldResponse response = iEggDetailView.getCodeSnippet().getObjectFromJsonStringRetro(mAppConfigurationManager.getHoldingData(), StockHoldResponse.class);
            if (response != null) {
                calculateInvestedAmountWithFor(response.getStockHoldList());
            }
        }
    }

    @Override
    public void setAuthenticationRequired(boolean isAuthenticationRequired) {
        mAppConfigurationManager.setPinAuthenticationRequired(isAuthenticationRequired);
    }

    @Override
    public boolean isHigh() {
        BigDecimal holding = new BigDecimal(mAppConfigurationManager.getLastKnownHoldingAmount());
        BigDecimal invested = new BigDecimal(mAppConfigurationManager.getLastKnownInvestedAmount());
        return holding.compareTo(invested) == 1;
    }

    @Override
    public void refreshContent(boolean canCallHoldings) {
        updateBalance();
        getHolding(canCallHoldings);
    }

    @Override
    public void callBalanceApi() {
        /*Log.d(TAG, "callBalanceApi isAccountVerified() " + mAppConfigurationManager.isFicaVerified());
        if (!(mAppConfigurationManager.isFicaVerified())) {
            return;
        }

        if (iEggDetailView.getCodeSnippet().hasNetworkConnection()) {
            Log.d("BalanceAPI Called", "From Egg Details");
            mBalanceModel.getBalance(new BaseRequest(mAppConfigurationManager.getUserId()));
        } else {
            iEggDetailView.showNetworkMessage();
        }*/
    }

    /***/
    private void callHoldingApi() {
        Log.d(TAG, "callHoldingApi isAccountVerified() " + mAppConfigurationManager.isFicaVerified());
        if (mAppConfigurationManager.isFicaVerified()) {
            if (iEggDetailView.getCodeSnippet().hasNetworkConnection()) {
                mGetHoldingModel.getHolding(0, new BaseRequest(mAppConfigurationManager.getUserId()));
            } else {
                iEggDetailView.showNetworkMessage();
            }
        }
    }

    /***/
    private void setUpHoldingAdapter(List<StockHold> stockHoldList) {
        if (stockHoldList == null) {
            return;
        }

        if (mHoldStockAdapter == null) {
            mHoldStockAdapter = new HoldStockAdapter(stockHoldList, adapterListener, iEggDetailView.getCodeSnippet());
            iEggDetailView.setHoldingAdapter(mHoldStockAdapter);
        } else {
            mHoldStockAdapter.resetItems(stockHoldList);
        }
    }


    /***/
    private BaseRecyclerAdapterListener<StockHold> adapterListener = new BaseRecyclerAdapterListener<StockHold>() {
        @Override
        public void onClickItem(StockHold data, int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.BundleKey.BUNDLE_STOCK_ITEM, data);
            iEggDetailView.navigateToSellBuy(bundle);

        }
    };

    /***/
    private IBaseModelListener<StockHoldResponse> stockHoldResponse = new IBaseModelListener<StockHoldResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, StockHoldResponse response) {
//            iEggDetailView.dismissProgressbar();
            if (response.getStockHoldList() != null) {
                mAppConfigurationManager.setHoldingData(iEggDetailView.getCodeSnippet().getJsonStringFromObject(response));
                calculateInvestedAmount(response.getStockHoldList());
            }
        }


        @Override
        public void onFailureApi(long taskId, CustomException e) {
//            iEggDetailView.dismissProgressbar();
            iEggDetailView.setRefreshing(false);
        }
    };

    BigDecimal totalInvestment, totalShare, totalHolding;

    @SuppressLint("StaticFieldLeak")
    private void calculateInvestedAmountWithFor(List<StockHold> stockHoldList) {

        totalInvestment = totalShare = totalHolding = BigDecimal.ZERO;

        new AsyncTask<Void, Void, List<StockHold>>() {

            @Override
            protected List<StockHold> doInBackground(Void... lists) {

                for (int i = 0; i < stockHoldList.size(); i++) {
                    StockHold stock = stockHoldList.get(i);
                    CompanyItemInfo info = mDatabaseManager.getCompanyItem(stock.getIsinCode());

                    if (info != null) {
                        stock.setCompanyLogo(info.getCompanyImageUrl());
                        stock.setClosingPrice(info.getLastPrice());
                        stock.setLastUpdatedPrice(info.getLastKnownDelayPrice());
                        stock.setMaxTradeAmount(info.getMaxTradeAmount());

                        BigDecimal currentInvestedAmount = new BigDecimal(TraderApplication.getInstance().getInvestedAmountBySharePrice(stock.getShares(), String.valueOf(stock.getBaseCost())));
                        BigDecimal currentHoldingAmount = new BigDecimal(TraderApplication.getInstance().getTotalHoldingByPrice(stock.getShares(), String.valueOf(info.getLastPrice())));

                        Log.d(i + " InvestedAmount", "" + currentInvestedAmount);
                        Log.d(i + " InvestedAmount", "" + currentHoldingAmount);

                        stock.setCurrentInvestment(String.valueOf(currentInvestedAmount));
                        stock.setCurrentHolding(String.valueOf(currentHoldingAmount));
                        stock.setCompanyAvailabilityStatus(info.getCompanyAvailabilityStatus());

                        totalInvestment = totalInvestment.add(currentInvestedAmount);
                        totalShare = totalShare.add(new BigDecimal(stock.getShares()));
                        totalHolding = totalHolding.add(currentHoldingAmount);
                        Log.d("InvestedAmount Tot", "" + totalInvestment);
                    }
                }

                mAppConfigurationManager.setLastKnownHoldingAmount(String.valueOf(totalHolding));
                mAppConfigurationManager.setLastKnownInvestedAmount(String.valueOf(totalInvestment));
                mAppConfigurationManager.setLastKnownShare(String.valueOf(totalShare));
                return stockHoldList;
            }

            @Override
            protected void onPostExecute(List<StockHold> stockHoldsList) {
                super.onPostExecute(stockHoldsList);
                setUpHoldingAdapter(stockHoldsList);
                setInvestmentDetails();
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    private void calculateInvestedAmount(List<StockHold> stockHoldList) {

        /*Total Holdings = No of shares bought for a company * 15 mins delayed price*/
        /* Invested Amount = No of shares bought for a compancy * Share price ( baseCost )*/
        /*differece = TH > IA = green : red*/
        /*Whole Invested amount : add all invested amount */
        /*shares = add all shares.*/

        totalInvestment = totalShare = totalHolding = BigDecimal.ZERO;

        new AsyncTask<Void, Void, List<StockHold>>() {

            @Override
            protected List<StockHold> doInBackground(Void... lists) {
//                final BigDecimal[] totalInvestment = {new BigDecimal(Constants.Common.DEFAULT_PRICE_ZERO)};
//                final BigDecimal[] totalShare = {new BigDecimal(Constants.Common.DEFAULT_PRICE_ZERO)};
//                final BigDecimal[] totalHolding = {new BigDecimal(Constants.Common.DEFAULT_PRICE_ZERO)};
                Stream.of(stockHoldList).forEach(stock -> {
                    CompanyItemInfo info = mDatabaseManager.getCompanyItem(stock.getIsinCode());

                    if (info != null) {
                        BigDecimal currentInvestedAmount = new BigDecimal(TraderApplication.getInstance().getInvestedAmountBySharePrice(stock.getShares(), String.valueOf(stock.getBaseCost())));
                        BigDecimal currentHoldingAmount = new BigDecimal(TraderApplication.getInstance().getTotalHoldingByPrice(stock.getShares(), String.valueOf(info.getLastPrice())));

                        stock.setCompanyLogo(info.getCompanyImageUrl());
                        stock.setClosingPrice(info.getLastPrice());
                        stock.setLastUpdatedPrice(info.getLastKnownDelayPrice());

                        Log.d("InvestedAmount", "" + currentInvestedAmount);
                        Log.d("InvestedAmount", "" + currentHoldingAmount);

                        stock.setCurrentInvestment(String.valueOf(currentInvestedAmount));
                        stock.setCurrentHolding(String.valueOf(currentHoldingAmount));
                        stock.setCompanyAvailabilityStatus(info.getCompanyAvailabilityStatus());

                        totalInvestment = totalInvestment.add(currentInvestedAmount);
                        totalHolding = totalHolding.add(currentHoldingAmount);
                        totalShare = totalShare.add(new BigDecimal(stock.getShares()));
//                        totalInvestment[0] = totalInvestment[0].add(currentInvestedAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
//                        totalHolding[0] = totalHolding[0].add(currentHoldingAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
//                        totalShare[0] = totalShare[0].add(new BigDecimal(stock.getShares())).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                });

                Log.d("InvestedAmount Tot", "" + totalInvestment);
                mAppConfigurationManager.setLastKnownHoldingAmount(String.valueOf(totalHolding));
                mAppConfigurationManager.setLastKnownInvestedAmount(String.valueOf(totalInvestment));
                mAppConfigurationManager.setLastKnownShare(String.valueOf(totalShare));
                return stockHoldList;
            }

            @Override
            protected void onPostExecute(List<StockHold> stockHoldsList) {
                super.onPostExecute(stockHoldsList);
//                iEggDetailView.dismissProgressbar();
                setUpHoldingAdapter(stockHoldsList);
                setInvestmentDetails();
            }
        }.execute();
    }

    /*private IBaseModelListener<BalanceResponse> balanceResponseListener = new IBaseModelListener<BalanceResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, BalanceResponse response) {
            Log.d(TAG, "onSuccessfulApi");
            if (response.getBalanceItems() == null) {
                return;
            }

            try {
                BalanceItem balanceItem = Stream.of(response.getBalanceItems())
                        .filter(balance -> balance.getAccountType().equalsIgnoreCase(mAppConfigurationManager.getTrustAccountType()))
                        .single();
                Log.d(TAG, "setBalanceData" + balanceItem);
                if (balanceItem != null) {
                    mAppConfigurationManager.setLastKnownBalance(String.valueOf(TraderApplication.getInstance().convertPriceCentToRand(balanceItem.getBalance())));
                    mAppConfigurationManager.setEftReferenceNumber(balanceItem.getEftReference());
                    mAppConfigurationManager.setBalanceUpdatedStatus(true);
                    iEggDetailView.setBalanceData(String.valueOf(TraderApplication.getInstance().convertPriceCentToRand(balanceItem.getBalance())));
                    iEggDetailView.sendBalanceUpdatedBroadCast();
                }
            } catch (IllegalStateException | NoSuchElementException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            //iEggDetailView.showMessage(e);
        }
    };*/
}
