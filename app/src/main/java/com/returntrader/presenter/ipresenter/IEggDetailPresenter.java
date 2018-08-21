package com.returntrader.presenter.ipresenter;

/**
 * Created by moorthy on 9/15/2017.
 */

public interface IEggDetailPresenter extends IPresenter {

    void getHolding(boolean canCallHoldings);

    void setAuthenticationRequired(boolean isAuthenticationRequired);

    boolean isHigh();

    void refreshContent(boolean canCallHoldings);

    void callBalanceApi();

    void updateBalance();

    void setShowFingerPrint(boolean canShow);
}

