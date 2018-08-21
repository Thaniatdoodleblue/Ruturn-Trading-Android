package com.returntrader.view.iview;

import android.os.Bundle;

import com.returntrader.adapter.HoldStockAdapter;

/**
 * Created by moorthy on 9/15/2017.
 */

public interface IEggDetailView extends IView {


    void setHoldingAdapter(HoldStockAdapter holdingAdapter);

    void setBalanceData(String lastKnownBalance);

    void setInvestmentDetails(String investmentAmount, String totalShare);

    void navigateToSellBuy(Bundle bundle);

    void refreshContent(boolean canCallHoldings);

    void sendBalanceUpdatedBroadCast();

    void setRefreshing(boolean isRefreshing);
}
