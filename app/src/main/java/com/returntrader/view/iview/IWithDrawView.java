package com.returntrader.view.iview;

import com.returntrader.model.common.WithDrawableBalanceData;

/**
 * Created by moorthy on 12/1/2017.
 */

public interface IWithDrawView extends IView {

    void setAccountInfo(WithDrawableBalanceData lastKnownBalance);

    void showNoticeDialog();

    void doAuthentication();

    void doUpdateBalance();
}
