package com.returntrader.view.iview;

import com.returntrader.adapter.HomeAdapter;
import com.returntrader.model.common.TopBarData;

/**
 * Created by moorthy on 9/7/2017.
 */

public interface IHomeView extends IView {

    void hideTopBannerCardView(int visibility);

    void setUpTopBar();

    void moveToHomeScreen();

    void showFicaVerifiedDialog();

    void startDelayPriceUpdateService();

    void updateHoldingsList();
}
