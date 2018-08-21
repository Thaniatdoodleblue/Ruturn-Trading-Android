package com.returntrader.presenter.ipresenter;

import com.returntrader.model.dto.request.WithDrawalRequest;

/**
 * Created by moorthy on 12/1/2017.
 */

public interface IWithDrawPresenter extends IPresenter {

    void callWithDrawableBalanceApi();

    String getLastKnownBalance();

    void doRequestWithdrawal(WithDrawalRequest request);

    void doAuthentication();

    void setAuthPinRequired(boolean isRequired);

    void doDelayPriceUpdate();
}
