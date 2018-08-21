package com.returntrader.view.iview;

import android.os.Bundle;

public interface IShakeMakeView extends IView {


    void navigateToShakeMakeConfirm(Bundle bundle);

    void unregisterShakeListener();
}
