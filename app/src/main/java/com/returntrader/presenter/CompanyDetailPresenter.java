package com.returntrader.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.annimon.stream.Stream;
import com.returntrader.adapter.CompanyDetailsAdapter;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.database.DatabaseManager;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.StockHold;
import com.returntrader.model.dto.request.BuyRequest;
import com.returntrader.model.dto.request.FavouriteStatusChangeRequest;
import com.returntrader.model.dto.response.BuyResponse;
import com.returntrader.model.dto.response.FavouriteResponse;
import com.returntrader.model.dto.response.StockHoldResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.BuyModel;
import com.returntrader.model.webservice.FavouriteModel;
import com.returntrader.presenter.ipresenter.ICompanyDetailPresenter;
import com.returntrader.view.iview.ICompanyDetailsView;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by moorthy on 7/20/2017.
 */

public class CompanyDetailPresenter extends BasePresenter implements ICompanyDetailPresenter {
    private ICompanyDetailsView iCompanyDetailsView;
    private CompanyDetailsAdapter mCompanyDetailsAdapter;
    private CompanyItemInfo mHomeSearchData;
    private FavouriteModel mFavouriteModel;
    private boolean isFavourite = false;
    private int mCurrentItemPosition = -1;
    private DatabaseManager mDatabaseManager;
    private BuyModel mBuyModel;
    private AppConfigurationManager mAppConfigurationManager;
    private boolean isCompanyAvailable;

    public CompanyDetailPresenter(ICompanyDetailsView iView) {
        super(iView);
        this.iCompanyDetailsView = iView;
        this.mFavouriteModel = new FavouriteModel(favouriteResponseIBaseModelListener);
        this.mBuyModel = new BuyModel(buyResponseIBaseModelListener);
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        this.mDatabaseManager = new DatabaseManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            mHomeSearchData = bundle.getParcelable(Constants.BundleKey.BUNDLE_COMPANY_ITEM);
            Long id = bundle.getLong(Constants.BundleKey.BUNDLE_COMPANY_ITEM_ID);
            mCurrentItemPosition = bundle.getInt(Constants.BundleKey.BUNDLE_COMPANY_ITEM_POSITION);
            String companyFrom = bundle.getString(Constants.BundleKey.BUNDLE_COMPANY_FROM);
            if (mHomeSearchData != null) {
                if (!TextUtils.isEmpty(mHomeSearchData.getCompanyAvailabilityStatus())) {
                    isCompanyAvailable = mHomeSearchData.getCompanyAvailabilityStatus().equalsIgnoreCase(Constants.Common.ENABLED_COMPANIES_STATUS) ? false : true;
                }
                isFavourite = isFavourite(mHomeSearchData.getFavourite());
                iCompanyDetailsView.setCompanyData(mHomeSearchData, companyFrom);
                iCompanyDetailsView.setFavouriteStatus(isFavourite);
                prepareViewPagerAdapter();
            }
        }

    }

    /***/
    private void prepareViewPagerAdapter() {
        if (mCompanyDetailsAdapter == null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.BundleKey.BUNDLE_COMPANY_ITEM, mHomeSearchData);
            mCompanyDetailsAdapter = new CompanyDetailsAdapter(iCompanyDetailsView.getActivity().getSupportFragmentManager(), bundle);
        }
        iCompanyDetailsView.setViewPagerAdapter(mCompanyDetailsAdapter);
    }


    /***/
    private boolean isFavourite(int isFavourite) {
        //Log.d(TAG,"isFavourite :" + isFavourite);
        return isFavourite == 1;
    }

    @Override
    public void onClickFavourite(boolean isFavourite) {
        toggleFavouriteStatus();
        startFavSync();
    }

    /***/
    private void startFavSync() {
        new AsyncTask<Void, Void, List<Long>>() {
            @Override
            protected List<Long> doInBackground(Void... voids) {
                return getUserFavListAsString();
            }

            @Override
            protected void onPostExecute(List<Long> favId) {
                super.onPostExecute(favId);
                if (favId != null) {
                    String id = Arrays.toString(favId.toArray());
                    if (!(TextUtils.isEmpty(id))) {
                        id = id.replaceAll("\\[", "").replaceAll("\\]", "");
                        Log.d(TAG, "favId.toString() : " + id);
                        if (!(TextUtils.isEmpty(id))) {
                            callFavApi(id);
                        }
                    }
                }
            }
        }.execute();
    }


    /***/
    private void callFavApi(String favId) {
        if (iCompanyDetailsView.getCodeSnippet().hasNetworkConnection() && !(TextUtils.isEmpty(mAppConfigurationManager.getUserId()))) {
            FavouriteStatusChangeRequest favouriteStatusChangeRequest = new FavouriteStatusChangeRequest();
            favouriteStatusChangeRequest.setUserId(mAppConfigurationManager.getUserId());
            favouriteStatusChangeRequest.setCompanyId(favId);
            mFavouriteModel.postFavouriteStatus(0, favouriteStatusChangeRequest);
        }
    }

    @Override
    public Bundle getUpdatedBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BundleKey.BUNDLE_COMPANY_ITEM, mHomeSearchData);
        bundle.putInt(Constants.BundleKey.BUNDLE_COMPANY_ITEM_POSITION, mCurrentItemPosition);
        return bundle;
    }


    /***/
    private IBaseModelListener<BuyResponse> buyResponseIBaseModelListener = new IBaseModelListener<BuyResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, BuyResponse response) {
            iCompanyDetailsView.dismissProgressbar();
            if (response != null) {
                if (response.isProcessed()) {
                    iCompanyDetailsView.doBalanceUpdate();
                    iCompanyDetailsView.postOrderProgress(Constants.TransactionType.RESPONSE_SUCCESS);
                    if (!mAppConfigurationManager.isMarketAvailable()) {
                        iCompanyDetailsView.showMessage("You are currently trading outstanding trading hours - trades would be proceed next working day");
                    }
                }
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iCompanyDetailsView.dismissProgressbar();
            iCompanyDetailsView.postOrderProgress(Constants.TransactionType.RESPONSE_FAILURE);
        }
    };


    /***/
    private void toggleFavouriteStatus() {
        isFavourite = !(isFavourite);
        int favourite = isFavourite ? 1 : 0;
        mHomeSearchData.setFavourite(favourite);
        iCompanyDetailsView.setFavouriteStatus(isFavourite);
        mDatabaseManager.doFavourite(mHomeSearchData);
    }


    /***/
    private IBaseModelListener<FavouriteResponse> favouriteResponseIBaseModelListener = new IBaseModelListener<FavouriteResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, FavouriteResponse response) {
            iCompanyDetailsView.dismissProgressbar();
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            if (e != null) {
                iCompanyDetailsView.showMessage(e.getException());
            }
        }
    };


    @Override
    public boolean isValidShareAmount() {
        return mHomeSearchData != null && mHomeSearchData.getLastPrice() > 0;
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

    @Override
    public String getMaxTradeAmount() {
        return mHomeSearchData.getMaxTradeAmount();
    }

    private BigDecimal totalInvestment, totalShare, totalHolding;

    @Override
    public void doDelayPriceUpdate() {
        StockHoldResponse response = iCompanyDetailsView.getCodeSnippet().getObjectFromJsonStringRetro(new AppConfigurationManager(iCompanyDetailsView.getActivity()).getHoldingData(), StockHoldResponse.class);
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

                                Log.d("CompanyD InvestedAmount", "" + currentInvestedAmount);
                                Log.d("CompanyD InvestedAmount", "" + currentHoldingAmount);

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

                        com.returntrader.library.Log.d("CompanyD InvestedAmountToT", "" + totalInvestment);
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
    public void doCheckCanIBuy() {
        if (isCompanyAvailable) {
            iCompanyDetailsView.doBuy();
        } else {
            iCompanyDetailsView.showHolidayDialog();
        }
    }

    @Override
    public void setAuthNeeded() {
        mAppConfigurationManager.setPinAuthenticationRequired(true);
        mAppConfigurationManager.setFingerPrintEnabledStatus(true);
    }

    @Override
    public boolean isCompanyAvailable() {
        return isCompanyAvailable;
    }

    /***/
    private void setCashAndInvestedDetails() {
        iCompanyDetailsView.setUpTopBar();
    }

    /***/
    private void doStockPurchase() {
        if (iCompanyDetailsView.getCodeSnippet().hasNetworkConnection()) {
//            iCompanyDetailsView.showProgressbar();
            BigDecimal convertedPrice = TraderApplication.getInstance().getBuyAmount(iCompanyDetailsView.getBuyAmount());
            Log.d(TAG, "Buy ConvertedPrice : " + String.format("%.0f", convertedPrice));
            mBuyModel.doBuyShare(1, new BuyRequest(mAppConfigurationManager.getUserId(), String.format("%.0f", convertedPrice), mHomeSearchData.getISINCode()));
        } else {
            iCompanyDetailsView.showNetworkMessage();
        }
    }


    /***/
    private String getFavouriteStatusMessage(boolean isFavourite) {
        return isFavourite ? "Added to Favorites" : "Removed from Favorites";
    }


    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResultPresenter : requestCode " + requestCode + "resultCode : " + resultCode);
        switch (requestCode) {
            case Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE:
                if (resultCode == Activity.RESULT_OK) {
                    if (!(TextUtils.isEmpty(iCompanyDetailsView.getBuyAmount()))) {
                        doStockPurchase();
                        iCompanyDetailsView.showQuestions();
                    }
                } else {
                    iCompanyDetailsView.showMessage("Please authenticate to buy a share");
                }
                break;
        }
    }

    /***/
    private List<Long> getUserFavListAsString() {
        return mDatabaseManager.getFavouriteCompany();
    }
}
