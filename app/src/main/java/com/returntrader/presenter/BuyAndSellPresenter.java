package com.returntrader.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.annimon.stream.Stream;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.database.DatabaseManager;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.helper.GraphManger;
import com.returntrader.model.common.LineGraphDataSet;
import com.returntrader.model.common.StockHold;
import com.returntrader.model.dto.response.StockHoldResponse;
import com.returntrader.presenter.ipresenter.IBuyAndSellPresenter;
import com.returntrader.view.iview.IBuyAndSellView;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by moorthy on 9/18/2017.
 */

public class BuyAndSellPresenter extends BasePresenter implements IBuyAndSellPresenter {
    private IBuyAndSellView iBuyAndSellView;
    private StockHold mCurrentStock;
    private GraphManger mGraphManger;
    private DatabaseManager mDatabaseManager;
    private AppConfigurationManager mAppConfigurationManager;
    boolean isCompanyAvailable;

    public BuyAndSellPresenter(IBuyAndSellView iView) {
        super(iView);
        this.iBuyAndSellView = iView;
        this.mGraphManger = new GraphManger(iView.getActivity(), onGraphLoadedListener);
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        this.mDatabaseManager = new DatabaseManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            mCurrentStock = bundle.getParcelable(Constants.BundleKey.BUNDLE_STOCK_ITEM);
            if (mCurrentStock != null) {
                iBuyAndSellView.setShareStockInfo(getCurrentStock());
                mGraphManger.fetchGraphDetails(getCurrentStock().getIsinCode());
                if (mCurrentStock != null) {
                    if (!TextUtils.isEmpty(mCurrentStock.getCompanyAvailabilityStatus())) {
                        isCompanyAvailable = mCurrentStock.getCompanyAvailabilityStatus().equalsIgnoreCase(Constants.Common.ENABLED_COMPANIES_STATUS) ? false : true;
                    }
                }
                mGraphManger.isCompanyAvailable(isCompanyAvailable);
            }
        }
    }

    private StockHold getCurrentStock() {
        return mCurrentStock;
    }

    @Override
    public void onLoad(int filterType) {
        mGraphManger.onLoad(filterType);
    }

    private BigDecimal totalInvestment, totalShare, totalHolding;

    @Override
    public void doDelayPriceUpdate() {
        StockHoldResponse response = iBuyAndSellView.getCodeSnippet().getObjectFromJsonStringRetro(new AppConfigurationManager(iBuyAndSellView.getActivity()).getHoldingData(), StockHoldResponse.class);
        if (response != null) {
        /*Total Holdings = No of shares bought for a company * 15 mins delayed price*/
        /* Invested Amount = No of shares bought for a compancy * Share price ( baseCost )*
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

                                Log.d("B & S InvestedAmount", "" + currentInvestedAmount);
                                Log.d("B & S InvestedAmount", "" + currentHoldingAmount);

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

                        com.returntrader.library.Log.d("B & S InvestedAmountToT", "" + totalInvestment);
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

    /***/
    private void setCashAndInvestedDetails() {
        iBuyAndSellView.setUpTopBar();
    }

    private GraphManger.OnGraphLoadedListener onGraphLoadedListener = new GraphManger.OnGraphLoadedListener() {
        @Override
        public void loadGraphView(LineGraphDataSet lineDataSet) {
            Log.d(TAG, "loadGraphView");
            iBuyAndSellView.setGraphView(lineDataSet);
        }

        @Override
        public void showGraphLoadingProgressBar() {
            iBuyAndSellView.showProgressbar();
        }

        @Override
        public void dismissGraphLoadingProgressBar() {
            iBuyAndSellView.dismissProgressbar();
        }

        @Override
        public void showNetworkMessage() {
            iBuyAndSellView.showNetworkMessage();
        }

        @Override
        public void setHighLowAmount(Float open, Float min, Float max) {
        }
    };

    @Override
    public void onClickSell() {
        if (isCompanyAvailable) {
            iBuyAndSellView.navigateToSell(getBundle());
        } else {
            iBuyAndSellView.showHolidayDialog();
        }
    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BundleKey.BUNDLE_STOCK_ITEM, mCurrentStock);
        return bundle;
    }

    @Override
    public void onClickBuy() {
        if (isCompanyAvailable) {
            iBuyAndSellView.navigateToBuy(getBundle());
        } else {
            iBuyAndSellView.showHolidayDialog();
        }
    }


    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCodes.NAVIGATE_TO_SELL:
                if (resultCode == Activity.RESULT_OK) {
                    iBuyAndSellView.getActivity().finish();
                }
                break;
            case Constants.RequestCodes.NAVIGATE_TO_BUY:
                if (resultCode == Activity.RESULT_OK) {
                    iBuyAndSellView.getActivity().finish();
                }
                break;
        }
    }
}
