package com.returntrader.view.iview;

import com.returntrader.adapter.ShakeMakeMoneyAdapter;
import com.returntrader.model.common.ShakeMakeMoneyData;

import java.util.List;

public interface IShakeMakeMoneyView extends IView {
    void navigateToGetReadyShake();

    void setShakeMakeMoneyAdapter(ShakeMakeMoneyAdapter shakeMakeGroupAdapter);

    void enableNextButton();

    void setDialogMoneyDesc(List<ShakeMakeMoneyData> shakeMakeMoneyDataList);

    void setSound();
}
