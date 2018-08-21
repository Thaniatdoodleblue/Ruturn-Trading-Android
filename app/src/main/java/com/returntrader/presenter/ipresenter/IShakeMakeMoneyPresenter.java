package com.returntrader.presenter.ipresenter;

import com.returntrader.model.common.ShakeMakeMoneyData;

public interface IShakeMakeMoneyPresenter extends IPresenter {
   void onClickNext();

    String getLastKnownBalance();

    ShakeMakeMoneyData getShakeMakeMoneyData();

    void showInfoAlert();

}
