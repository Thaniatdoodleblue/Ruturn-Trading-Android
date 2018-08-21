package com.returntrader.view.iview;

import android.os.Bundle;

import com.returntrader.model.common.ShakeMakeCompanyData;

public interface IShakeMakeConfirmView extends IView {
    void   navigateToShakeMakeSuccess(Bundle bundle);

    void doUpdateBalance();

    void setShakeMakeCompanyData(ShakeMakeCompanyData shakeMakeCompanyData);
}
