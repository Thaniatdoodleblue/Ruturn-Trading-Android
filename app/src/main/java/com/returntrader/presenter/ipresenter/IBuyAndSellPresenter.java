package com.returntrader.presenter.ipresenter;

/**
 * Created by moorthy on 9/18/2017.
 */

public interface IBuyAndSellPresenter extends IPresenter {
    void onClickSell();

    void onClickBuy();

    void onLoad(int filterType);

    void doDelayPriceUpdate();
}
