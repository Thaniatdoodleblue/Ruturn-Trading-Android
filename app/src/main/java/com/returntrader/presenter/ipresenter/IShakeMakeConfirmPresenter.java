package com.returntrader.presenter.ipresenter;

import com.returntrader.model.common.ShakeMakeMoneyData;

public interface IShakeMakeConfirmPresenter extends IPresenter {
    void doDelayPriceUpdate();

    ShakeMakeMoneyData getShakeMakeMoneyData();

    void setNavigatingToShakeMakeSuccess(String msg);

    boolean hasTransactionAccess();

    String getLastKnownBalance();

    boolean isMarketAvailable();

    void setAuthNeeded();

    void doBuyApi(ShakeMakeMoneyData shakeMakeMoneyData);
}
