package com.returntrader.view.iview;

import com.returntrader.adapter.ShakeMakeGroupAdapter;

public interface IShakeMakeGroupView extends IView {
    void setGroupAdapter(ShakeMakeGroupAdapter shakeMakeGroupAdapter);

    void enableNextButton();

    void navigateToShakeMakeMoney();

    void setSound();
}
