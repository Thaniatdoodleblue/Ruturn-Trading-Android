package com.returntrader.view.iview;

/**
 * Created by moorthy on 11/14/2017.
 */

public interface ISplashView extends IView {

    void startStatusCheckService(int flag);

    void startDelayPriceUpdateService();

    void navigateToNexScreen(long milliSeconds);
}
