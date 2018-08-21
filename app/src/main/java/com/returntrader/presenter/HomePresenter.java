package com.returntrader.presenter;

import android.os.AsyncTask;
import android.os.Bundle;

import com.annimon.stream.Stream;
import com.returntrader.common.RetroFitBase;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.database.DatabaseManager;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.Log;
import com.returntrader.model.common.StockHold;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.response.BankVerifyResponse;
import com.returntrader.model.dto.response.StockHoldResponse;
import com.returntrader.model.webservice.ApiClient;
import com.returntrader.model.webservice.ApiInterface;
import com.returntrader.model.webservice.executor.IGeneralResponseListener;
import com.returntrader.presenter.ipresenter.IHomePresenter;
import com.returntrader.view.iview.IHomeView;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;

import static com.returntrader.common.Constants.RequestCodes.TASKID_VERIFYBANK;

/**
 * Created by moorthy on 9/7/2017.
 */

public class HomePresenter extends BasePresenter implements IHomePresenter {
    private IHomeView iHomeView;
    private AppConfigurationManager mAppConfigurationManager;
    //    private Handler mSliderHandler;
//    private Runnable mSliderRunnable;
    private DatabaseManager mDatabaseManager;


    public HomePresenter(IHomeView iView) {
        super(iView);
        this.iHomeView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        this.mDatabaseManager = new DatabaseManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
//        startTimer();
//        setCashAndInvestedDetails();

        if (mAppConfigurationManager.isFicaVerifyPopUpEnabled()) {
            mAppConfigurationManager.setFicaPopUpShownStatus(true);
            //iHomeView.showFicaVerifiedDialog();
        }
        doDelayPriceUpdate();
        setCashAndInvestedDetails();
    }

    /***/
   /* private void startTimer() {
        mSliderHandler = new Handler();
        mSliderRunnable = () -> {
            iHomeView.startDelayPriceUpdateService();
            mSliderHandler.postDelayed(mSliderRunnable, Constants.Common.REFRESH_TIME_DELAY);
        };
        mSliderHandler.postDelayed(mSliderRunnable, Constants.Common.REFRESH_TIME_DELAY);
    }*/
    @Override
    public boolean isPinAuthenticationEnabled() {
        return mAppConfigurationManager.isPinAuthenticationRequired();
    }

    @Override
    public void setCashAndInvestedDetails() {
        iHomeView.setUpTopBar();
    }


    @Override
    public void updateBalance() {
        setCashAndInvestedDetails();
    }

    private BigDecimal totalInvestment, totalShare, totalHolding;

    @Override
    public void doDelayPriceUpdate() {
        StockHoldResponse response = iHomeView.getCodeSnippet().getObjectFromJsonStringRetro(new AppConfigurationManager(iHomeView.getActivity()).getHoldingData(), StockHoldResponse.class);
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

                                android.util.Log.d("Home InvestedAmount", "" + currentInvestedAmount);
                                android.util.Log.d("Home InvestedAmount", "" + currentHoldingAmount);

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

                        Log.d("Home InvestedAmountToT", "" + totalInvestment);
                        mAppConfigurationManager.setLastKnownHoldingAmount(String.valueOf(totalHolding));
                        mAppConfigurationManager.setLastKnownInvestedAmount(String.valueOf(totalInvestment));
                        mAppConfigurationManager.setLastKnownShare(String.valueOf(totalShare));
                        return stockHoldList;
                    }

                    @Override
                    protected void onPostExecute(List<StockHold> stockHoldsList) {
                        super.onPostExecute(stockHoldsList);
                        setCashAndInvestedDetails();
                        iHomeView.updateHoldingsList();
                    }
                }.execute();
            }
        }
    }
}
