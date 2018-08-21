package com.returntrader.presenter.ipresenter;

/**
 * Created by moorthy on 9/7/2017.
 */

public interface IHomePresenter extends IPresenter {

    boolean isPinAuthenticationEnabled();

    void setCashAndInvestedDetails();

    void updateBalance();

    void doDelayPriceUpdate();
}
