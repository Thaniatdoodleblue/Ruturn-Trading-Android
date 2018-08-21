package com.returntrader.presenter.ipresenter;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by moorthy on 7/20/2017.
 */

public interface ICompanyDetailPresenter extends IPresenter {

    void onClickFavourite(boolean isFavourite);

    Bundle getUpdatedBundle();

    boolean isValidShareAmount();

    boolean hasTransactionAccess();

    String getLastKnownBalance();

    boolean isMarketAvailable();

    String getMaxTradeAmount();

    void doDelayPriceUpdate();

    void doCheckCanIBuy();

    void setAuthNeeded();

    boolean isCompanyAvailable();
}
