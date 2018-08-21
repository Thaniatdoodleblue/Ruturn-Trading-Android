package com.returntrader.presenter.ipresenter;

import java.math.BigDecimal;

/**
 * Created by moorthy on 9/20/2017.
 */

public interface ISellStockPresenter extends IPresenter {

    void calculatePercentage(String value);

    void calculateRand(String percentageValue);

    BigDecimal getCurrentAvailableSell();

    boolean isMarketAvailable();

    void doDelayPriceUpdate();

    void setAuthNeeded();

    String getLastKnownBalance();
}
