package com.returntrader.presenter.ipresenter;

import com.returntrader.model.common.StockHold;

/**
 * Created by moorthy on 9/20/2017.
 */

public interface IBuyStockPresenter extends IPresenter {

    void calculateTransactionCost(String amountShare);

    boolean isValidShareAmount();

    boolean hasTransactionAccess();

    String getLastKnownBalance();

    boolean isMarketAvailable();

    StockHold getCurrentStock();

    void doDelayPriceUpdate();

    void setAuthNeeded();
}
